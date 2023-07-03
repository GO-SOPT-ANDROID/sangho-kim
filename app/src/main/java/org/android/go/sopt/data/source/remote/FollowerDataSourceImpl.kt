package org.android.go.sopt.data.source.remote

import org.android.go.sopt.data.entity.remote.FollowerResponseDTO
import org.android.go.sopt.data.service.FollowerService
import javax.inject.Inject

class FollowerDataSourceImpl @Inject constructor(
    private val followerService: FollowerService
) : FollowerDataSource {
    override suspend fun getFollowerList(page: Int): FollowerResponseDTO {
        return followerService.getList(page)
    }
}