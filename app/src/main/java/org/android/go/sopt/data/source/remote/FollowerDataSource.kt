package org.android.go.sopt.data.source.remote

import org.android.go.sopt.data.entity.remote.FollowerResponseDTO

interface FollowerDataSource {
    suspend fun getFollowerList(page: Int) : FollowerResponseDTO
}