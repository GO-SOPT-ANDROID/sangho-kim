package org.android.go.sopt.data.service

import org.android.go.sopt.data.entity.remote.FollowerResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface FollowerService {

    @GET("api/users")
    suspend fun getList(
        @Query("page") page: Int
    ): FollowerResponseDTO
}