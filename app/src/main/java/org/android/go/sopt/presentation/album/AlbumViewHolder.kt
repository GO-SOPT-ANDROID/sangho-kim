package org.android.go.sopt.presentation.album

import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.databinding.ItemAlbumBinding

class AlbumViewHolder(val binding: ItemAlbumBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(src: Int) {
        binding.imgAlbum.setImageResource(src)
    }
}