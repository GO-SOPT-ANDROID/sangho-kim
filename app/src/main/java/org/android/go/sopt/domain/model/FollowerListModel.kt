package org.android.go.sopt.domain.model

data class FollowerListModel(
    val page: Int,
    val perPage: Int,
    val total: Int,
    val totalPages: Int,
    val data: List<FollowerModel>
)