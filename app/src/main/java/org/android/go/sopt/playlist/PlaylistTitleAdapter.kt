package org.android.go.sopt.playlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.databinding.ItemPlaylistTitleBinding

class PlaylistTitleAdapter(context: Context) : RecyclerView.Adapter<PlaylistTitleViewHolder>() {

    private val inflater by lazy { LayoutInflater.from(context)}
    private var itemList: List<PlaylistTitle> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistTitleViewHolder {
        val binding: ItemPlaylistTitleBinding = ItemPlaylistTitleBinding.inflate(inflater, parent, false)
        return PlaylistTitleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistTitleViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

    override fun getItemCount() = itemList.size

    fun setItemList(itemList: List<PlaylistTitle>) {
        this.itemList = itemList.toList()
        notifyDataSetChanged()
    }
}