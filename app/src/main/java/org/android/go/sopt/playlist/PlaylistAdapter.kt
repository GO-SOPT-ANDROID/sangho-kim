package org.android.go.sopt.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import org.android.go.sopt.databinding.ItemPlaylistSongBinding

class PlaylistAdapter : ListAdapter<PlaylistSong, PlaylistViewHolder>(PlaylistDiffCallback) {

    private var itemList: List<PlaylistSong> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val binding: ItemPlaylistSongBinding = ItemPlaylistSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaylistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}

object PlaylistDiffCallback : DiffUtil.ItemCallback<PlaylistSong>() {
    override fun areItemsTheSame(oldItem: PlaylistSong, newItem: PlaylistSong): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PlaylistSong, newItem: PlaylistSong): Boolean {
        return oldItem == newItem
    }
}