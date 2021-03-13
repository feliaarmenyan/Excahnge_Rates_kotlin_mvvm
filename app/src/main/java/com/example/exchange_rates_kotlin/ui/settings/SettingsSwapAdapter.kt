package com.example.exchange_rates_kotlin.ui.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.exchange_rates_kotlin.data.remote.model.Currency

class SettingsSwapAdapter
//    (
//    private var items: MutableList<Currency>
//) : RecyclerView.Adapter<BaseHomeViewHolder>() {
//
//    var onTrackItemClick: ((Int, Currency, View) -> Unit)? = null
//
//    fun setTracks(elements: List<Currency>) {
//        val size = items.size
//        this.items.clear()
//        notifyItemRangeRemoved(0, size)
//        if (isWithHeader) {
//            this.items.add(ItemAllTrackTitle)
//        }
//        for (element in elements) {
//            val it = ItemTracks(element)
//            this.items.add(it as TracksWithTitle)
//        }
//        notifyItemRangeChanged(0, items.size)
//    }
//
//    fun addTracks(elements: List<Currency>) {
//        val positionStart: Int = this.items.size
//        val itemsList = mutableListOf<TracksWithTitle>()
//        for (element in elements) {
//            val it = ItemTracks(element)
//            itemsList.add(it as TracksWithTitle)
//        }
//        this.items.addAll(itemsList)
//        notifyItemRangeChanged(positionStart, elements.size)
//    }
//
//    /**
//     * Function called to swap dragged items
//     */
//    fun swapItems(
//        fromPosition: Int,
//        toPosition: Int
//    ): List<PlaylistUpdatePayload.TrackPositionPayload> {
//        if (fromPosition < toPosition) {
//            for (i in fromPosition until toPosition) {
//                items[i] = items.set(i + 1, items[i]);
//            }
//        } else {
//            for (i in fromPosition..toPosition + 1) {
//                items[i] = items.set(i - 1, items[i]);
//            }
//        }
//
//        notifyItemMoved(fromPosition, toPosition)
//        val trackPositions = mutableListOf<PlaylistUpdatePayload.TrackPositionPayload>()
//        items.filterIsInstance<ItemTracks>().map { it.track }.forEachIndexed { index, track ->
//            trackPositions.add(PlaylistUpdatePayload.TrackPositionPayload(track.id, index))
//        }
//
//        return trackPositions
//    }
//
//    fun getItems(): ArrayList<Currency> {
//        val el = arrayListOf<Currency>()
//        for (element in items) {
//            if (element is ItemTracks) {
//                el.add(element.track)
//            }
//        }
//        return el
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHomeViewHolder {
//        val layoutInflater = LayoutInflater.from(parent.context)
//        val binding = ItemTrackSmallBinding.inflate(layoutInflater, parent, false)
//        return  TrackViewHolder(binding)
//    }
//
//    abstract class BaseHomeViewHolder(viewDataBinding: ViewBinding) :
//        RecyclerView.ViewHolder(viewDataBinding.root) {
//        abstract fun bindTo(track: TracksWithTitle)
//    }
//
//
//    override fun onBindViewHolder(holder: BaseHomeViewHolder, position: Int) {
//        holder.bindTo(items[position])
//    }
//
//    inner class TrackViewHolder(private var binding: ItemTrackSmallBinding) :
//        BaseHomeViewHolder(binding) {
//        override fun bindTo(track: TracksWithTitle) {
//            val currentTrack = track as ItemTracks
//            with(binding) {
//                smallTrackItemTitle.text = currentTrack.track.titleSubTitle()
//                smallTrackItemArtist.text = currentTrack.track.artistsFullNames()
//
//                smallTrackItemImage.loadImage(
//                    currentTrack.track.coverImage?.formattedPath(
//                        200,
//                        200
//                    )
//                )
//
//                smallTrackItemLinear.setOnClickListener {
//                    notifyItemChanged(bindingAdapterPosition)
//                    if (isWithHeader) {
//                        onTrackItemClick?.invoke(
//                            bindingAdapterPosition - 1,
//                            currentTrack.track,
//                            it
//                        )
//                    } else {
//                        onTrackItemClick?.invoke(bindingAdapterPosition, currentTrack.track, it)
//                    }
//                }
//
//                track.track.contentWarning?.let {
//                    if (it) {
//                        contentWarning.visible(false)
//                    } else {
//                        contentWarning.gone(false)
//                    }
//                }
//
//                trackItemSettings.setOnClickListener {
//                    if (isWithHeader) {
//                        onTrackItemClick?.invoke(
//                            bindingAdapterPosition - 1,
//                            currentTrack.track,
//                            it
//                        )
//                    } else {
//                        onTrackItemClick?.invoke(bindingAdapterPosition, currentTrack.track, it)
//                    }
//                }
//
//                track.track.playerStatus?.let {
//                    when (it) {
//                        PlayerStatus.PLAY -> {
//                            indicator.visible(false)
//                            indicator.setIsStop(false)
//                            smallTrackItemConstrainLayout.isSelected = true
//                        }
//                        PlayerStatus.PAUSE -> {
//                            indicator.visible(false)
//                            indicator.setIsStop(true)
//                            smallTrackItemConstrainLayout.isSelected = true
//                        }
//                        PlayerStatus.STOP ->{
//                            indicator.gone(false)
//                            smallTrackItemConstrainLayout.isSelected = false
//                        }
//                    }
//                } ?: run {
//                    indicator.gone(false)
//                }
//            }
//        }
//    }
//
//    override fun getItemCount(): Int =
//        items.size
//}