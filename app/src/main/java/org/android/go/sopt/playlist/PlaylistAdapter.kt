package org.android.go.sopt.playlist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.ListAdapter
import org.android.go.sopt.databinding.ItemPlaylistSongBinding

class PlaylistAdapter : ListAdapter<PlaylistSong, PlaylistViewHolder>(PlaylistDiffCallback) {

    val selectionList = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val binding: ItemPlaylistSongBinding =
            ItemPlaylistSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaylistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.onBind(getItem(position))

        holder.chkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectionList.add(getItem(position).id)
            } else {
                selectionList.remove(getItem(position).id)
            }
        }
    }
}