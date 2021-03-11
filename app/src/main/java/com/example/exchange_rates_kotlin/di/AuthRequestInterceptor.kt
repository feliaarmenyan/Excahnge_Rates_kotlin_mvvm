package com.example.exchange_rates_kotlin.di

import android.content.Context
import android.content.Intent
import android.os.ConditionVariable
import android.text.TextUtils
import com.example.exchange_rates_kotlin.data.local.preference.AppPreferences
import com.example.exchange_rates_kotlin.ui.MainActivity
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.HttpURLConnection
import java.util.concurrent.atomic.AtomicBoolean

class AuthRequestInterceptor(
    private val context: Context,
    private val appPreferences: AppPreferences,
) : Interceptor {

    companion object {
        // these two static variables serve for the pattern to refresh a token
        private val LOCK = ConditionVariable(true)
        private val mIsRefreshing = AtomicBoolean(false)
    }

    private fun newRequestWithAccessToken(request: Request, accessToken: String): Request {
        return request.newBuilder()
            .header("x-access-token", accessToken)
            .build()
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        // 1. sign this request
        if (appPreferences.isUserLogged) {
            val accessToken = appPreferences.accessToken
            request = newRequestWithAccessToken(request, accessToken)
        }


        // 2. proceed with the request
        var response = chain.proceed(request)

        // 3. check the response: have we got a 401?
//        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
//            val token = appPreferences.accessToken
//            if (!TextUtils.isEmpty(token)) {
//                /*
//                 *  Because we send out multiple HTTP requests in parallel, they might all list a 401 at the same time.
//                 *  Only one of them should refresh the token, because otherwise we'd refresh the same token multiple times
//                 *  and that is bad. Therefore we have these two static objects, a ConditionVariable and a boolean. The
//                 *  first thread that gets here closes the ConditionVariable and changes the boolean flag.
//                 */
//                if (mIsRefreshing.compareAndSet(false, true)) {
//                    LOCK.close()
//
//                    // do we have an access token to refresh?
//                    val refreshToken = appPreferences.refreshToken
//                    if (!TextUtils.isEmpty(refreshToken)) {
//                        try {
//                            val jwtModel =
//                                authApiService!!.refreshToken(refreshToken, "refresh_token")
//                                    .execute().body()
//                            if (jwtModel == null) {
//                                // todo log out
//                                // logOut();
//                            } else {
//                                appPreferences.accessToken = jwtModel.accessToken
//
//                                appPreferences.refreshToken = jwtModel.refreshToken
//
//                                request =
//                                    newRequestWithAccessToken(request, jwtModel.accessToken)
//
//                                // return the outcome of the newly signed request
//                                response.close()
//                                response = chain.proceed(request)
//                            }
//                        } catch (e: Exception) {
//                            e.printStackTrace()
////                            logOut()
//                        }
//                    }
//                    LOCK.open()
//                    mIsRefreshing.set(false)
//                } else {
//                    // Another thread is refreshing the token for us, let's wait for it.
//                    val conditionOpened = LOCK.block(5000)
//
//                    // If the next check is false, it means that the timeout expired, that is - the refresh
//                    // stuff has failed. The thread in charge of refreshing the token has taken care of
//                    // redirecting the user to the login fragment.
//                    if (conditionOpened) {
//                        // another thread has refreshed this for us! thanks!
//                        val accessToken = appPreferences.accessToken
//                        // sign the request with the new token and proceed
//                        request = newRequestWithAccessToken(request, accessToken)
//                        response.close()
//                        // return the outcome of the newly signed request
//                        response = chain.proceed(request)
//                    }
//                }
//            }
//        }
        // check if still unauthorized (i.e. refresh failed)
        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            // clean your access token and prompt user for login again.
            logOut()
        }

        // returning the response to the original request
        return response
    }

    private fun logOut() {
        appPreferences.clearAllPrefData()
        val i = Intent(context, MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(i)
    }
}