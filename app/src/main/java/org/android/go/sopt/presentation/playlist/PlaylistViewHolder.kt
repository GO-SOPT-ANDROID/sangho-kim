package org.android.go.sopt.presentation.playlist

import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.data.local.PlaylistSong
import org.android.go.sopt.databinding.ItemPlaylistSongBinding

class PlaylistViewHolder(val binding: ItemPlaylistSongBinding) :
    RecyclerView.ViewHolder(binding.root) {

    var chkBox = binding.checkBox

    fun onBind(item: PlaylistSong) {
        binding.ivAlbum.setImageDrawable(binding.root.context.getDrawable(item.album))
        binding.tvArtist.text = item.artist
        binding.tvTitle.text = item.title
        binding.checkBox.isChecked = false
    }
}