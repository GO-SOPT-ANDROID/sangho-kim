package org.android.go.sopt.playlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.databinding.ItemPlaylistSongBinding

class PlaylistAdapter(context: Context) : RecyclerView.Adapter<PlaylistViewHolder>() {

    private val inflater by lazy { LayoutInflater.from(context)}
    private var itemList: List<PlaylistSong> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val binding: ItemPlaylistSongBinding = ItemPlaylistSongBinding.inflate(inflater, parent, false)
        return PlaylistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

    override fun getItemCount() = itemList.size

    fun setItemList(itemList: List<PlaylistSong>) {
        this.itemList = itemList.toList()
        notifyDataSetChanged()
    }
}