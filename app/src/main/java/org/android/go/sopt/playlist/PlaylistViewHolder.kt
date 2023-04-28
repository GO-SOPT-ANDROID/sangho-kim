package org.android.go.sopt.playlist

import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.databinding.ItemPlaylistSongBinding

class PlaylistViewHolder(private val binding: ItemPlaylistSongBinding) :
    RecyclerView.ViewHolder(binding.root) {

    val chkBox = binding.checkBox

    fun onBind(item: PlaylistSong) {
        binding.ivAlbum.setImageDrawable(binding.root.context.getDrawable(item.album))
        binding.tvArtist.text = item.artist
        binding.tvTitle.text = item.title

    }
}