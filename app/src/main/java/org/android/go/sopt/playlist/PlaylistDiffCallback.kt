package org.android.go.sopt.playlist

import androidx.recyclerview.widget.DiffUtil

object PlaylistDiffCallback : DiffUtil.ItemCallback<PlaylistSong>() {
    override fun areItemsTheSame(oldItem: PlaylistSong, newItem: PlaylistSong): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PlaylistSong, newItem: PlaylistSong): Boolean {
        return oldItem == newItem
    }
}