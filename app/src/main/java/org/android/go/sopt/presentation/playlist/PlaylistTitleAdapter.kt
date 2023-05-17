package org.android.go.sopt.presentation.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.android.go.sopt.data.local.PlaylistTitle
import org.android.go.sopt.databinding.ItemPlaylistTitleBinding
import org.android.go.sopt.util.ItemDiffCallback

class PlaylistTitleAdapter() :
    ListAdapter<PlaylistTitle, PlaylistTitleViewHolder>(
        ItemDiffCallback<PlaylistTitle>(
        onContentsTheSame = { old, new -> old == new },
        onItemsTheSame = { old, new -> old.id == new.id })
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistTitleViewHolder {
        val binding: ItemPlaylistTitleBinding =
            ItemPlaylistTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaylistTitleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistTitleViewHolder, position: Int) {}
}