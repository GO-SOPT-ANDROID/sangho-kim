package org.android.go.sopt.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.android.go.sopt.databinding.ItemPlaylistTitleBinding

class PlaylistTitleAdapter :
    ListAdapter<PlaylistTitle, PlaylistTitleViewHolder>(PlaylistTitleDiffCallback) {

    private var itemList: List<PlaylistTitle> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistTitleViewHolder {
        val binding: ItemPlaylistTitleBinding =
            ItemPlaylistTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaylistTitleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistTitleViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}