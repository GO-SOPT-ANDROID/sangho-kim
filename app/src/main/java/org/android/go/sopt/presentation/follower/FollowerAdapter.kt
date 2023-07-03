package org.android.go.sopt.presentation.follower

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.android.go.sopt.data.entity.remote.FollowerResponseDTO
import org.android.go.sopt.databinding.ItemFollowerBinding
import org.android.go.sopt.domain.model.FollowerModel
import org.android.go.sopt.util.ItemDiffCallback

class FollowerAdapter :
    ListAdapter<FollowerModel, FollowerViewHolder>(
        ItemDiffCallback<FollowerModel>(
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