package org.android.go.sopt.presentation.follower

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.android.go.sopt.databinding.ItemFollowerBinding
import org.android.go.sopt.data.remote.FollowerResponseDTO
import org.android.go.sopt.util.ItemDiffCallback

class FollowerAdapter :
    ListAdapter<FollowerResponseDTO.User, FollowerViewHolder>(
        ItemDiffCallback<FollowerResponseDTO.User>(
        onContentsTheSame = { old, new -> old == new },
        onItemsTheSame = { old, new -> old.id == new.id })
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val binding: ItemFollowerBinding =
            ItemFollowerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}