package org.android.go.sopt.playlist

import androidx.recyclerview.widget.DiffUtil

object PlaylistTitleDiffCallback : DiffUtil.ItemCallback<PlaylistTitle>() {
    override fun areItemsTheSame(oldItem: PlaylistTitle, newItem: PlaylistTitle): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PlaylistTitle, newItem: PlaylistTitle): Boolean {
        return oldItem == newItem
    }
}