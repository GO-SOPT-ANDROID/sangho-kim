package org.android.go.sopt.presentation.playlist

import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.android.go.sopt.data.entity.remote.MusicData
import org.android.go.sopt.databinding.ItemPlaylistSongBinding

class PlaylistViewHolder(val binding: ItemPlaylistSongBinding) :
    RecyclerView.ViewHolder(binding.root) {

    var chkBox = binding.checkBox

    fun onBind(item: MusicData) {
        binding.ivAlbum.load(item.url)
        binding.tvArtist.text = item.singer
        binding.tvTitle.text = item.title
        binding.checkBox.isChecked = false
    }
}