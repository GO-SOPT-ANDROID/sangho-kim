package org.android.go.sopt.presentation.follower

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import org.android.go.sopt.databinding.ItemFollowerBinding
import org.android.go.sopt.domain.model.FollowerModel

class FollowerViewHolder(val binding: ItemFollowerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: FollowerModel) {
        binding.tvFollowerName.text = item.name
        binding.tvFollowerEmail.text = item.email
        binding.ivFollowerImage.load(item.image) {
            transformations(RoundedCornersTransformation(10.0F))
        }
    }
}