package org.android.go.sopt.presentation.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.android.go.sopt.data.remote.MusicData
import org.android.go.sopt.databinding.ItemPlaylistSongBinding
import org.android.go.sopt.util.ItemDiffCallback

class PlaylistAdapter :
    ListAdapter<MusicData, PlaylistViewHolder>(
        ItemDiffCallback<MusicData>(
        onContentsTheSame = { old, new -> old == new },
        onItemsTheSame = { old, new -> old.url == new.url})
    ) {

    private var selectionList = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val binding: ItemPlaylistSongBinding =
            ItemPlaylistSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaylistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.onBind(getItem(position))
        holder.chkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectionList.add(position)
            } else {
                selectionList.remove(position)
            }
        }
    }
}