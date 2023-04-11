package org.android.go.sopt

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.databinding.ItemSongBinding

class MyAdapter(context: Context) : RecyclerView.Adapter<MyViewHolder>() {

    private val inflater by lazy { LayoutInflater.from(context)}
    private var itemList: List<Song> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemSongBinding = ItemSongBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

    override fun getItemCount() = itemList.size

    fun setItemList(itemList: List<Song>) {
        this.itemList = itemList.toList()
        notifyDataSetChanged()
    }
}

class MyViewHolder (private val binding: ItemSongBinding) :
RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: Song) {
        binding.ivAlbum.setImageDrawable(binding.root.context.getDrawable(item.album))
        binding.tvArtist.text = item.artist
        binding.tvTitle.text = item.title
    }
}

data class Song(
    @DrawableRes val album: Int,
    val artist: String,
    val title: String
)