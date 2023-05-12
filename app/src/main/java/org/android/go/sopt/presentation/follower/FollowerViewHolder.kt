package org.android.go.sopt.presentation.follower

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.android.go.sopt.databinding.ItemFollowerBinding
import org.android.go.sopt.remote.follower.FollowerResponseDTO

class FollowerViewHolder(val binding: ItemFollowerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: FollowerResponseDTO.User) {
        Glide.with(binding.root.context).load(item.avatar).into(binding.ivFollowerImage)
        val name = item.first_name + " " + item.last_name
        binding.tvFollowerName.text = name
        binding.tvFollowerEmail.text = item.email
    }
}