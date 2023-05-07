package org.android.go.sopt.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.android.go.sopt.databinding.ItemAlbumBinding
import org.android.go.sopt.util.ItemDiffCallback

class AlbumAdapter :
    ListAdapter<Int, AlbumViewHolder>(
        ItemDiffCallback<Int>(onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old == new })
    ) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding: ItemAlbumBinding =
            ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}