package com.example.exchange_rates_kotlin.di

import android.content.Context
import com.example.exchange_rates_kotlin.AppConstants.BASE_URL
import com.example.exchange_rates_kotlin.core.utils.hasNetworkConnection
import com.example.exchange_rates_kotlin.data.local.preference.AppPreferences
import com.example.exchange_rates_kotlin.data.remote.api.DailyExRatesApiService
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


val networkModule = module {

    single {
        val cacheSize = 10 * 1024 * 1024.toLong() // 10 MB
        val httpCacheDirectory = File(androidContext().cacheDir, "http-cache")
        Cache(httpCacheDirectory, cacheSize)
    }


    single {
        createWebService<DailyExRatesApiService>(
            okHttpClient = createSecureHttpClient(
                cache = get(),
                context = androidContext(),
                preference = get(),
            ),
            baseUrl = BASE_URL
        )
    }
}

/* Returns a custom OkHttpClient instance with interceptor. Used for building Retrofit service */
fun createHttpClient(
    cache: Cache,
    context: Context
): OkHttpClient {
    val client = OkHttpClient.Builder()

    client.apply {
        connectTimeout(30, TimeUnit.SECONDS)
        readTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
        cache(cache)
        addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        addInterceptor(createNetworkInterceptor(context))
    }

    return client.build()
}

fun createSecureHttpClient(
    cache: Cache,
    context: Context,
    preference: AppPreferences,
): OkHttpClient {

    val client = OkHttpClient.Builder()
    client.apply {
        connectTimeout(30, TimeUnit.SECONDS)
        readTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
        cache(cache)
        addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        addInterceptor(createNetworkInterceptor(context))
    }

    return client.build()
}


fun createNetworkInterceptor(context: Context): Interceptor {
    return Interceptor { chain: Interceptor.Chain ->
        val request = chain.request()
        if (context.hasNetworkConnection()) {
            request.newBuilder()
                .header("Cache-Control", "public, max-age=" + 60)
                .build()
        } else {
            request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                .build()
        }
        chain.proceed(request)
    }
}

/* function to build our Retrofit service */
inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    baseUrl: String
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(okHttpClient)
        .build()
    return retrofit.create(T::class.java)
}