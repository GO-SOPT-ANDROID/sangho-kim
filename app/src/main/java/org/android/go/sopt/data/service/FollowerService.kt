package org.android.go.sopt.data.service

import org.android.go.sopt.data.remote.FollowerResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FollowerService {

    @GET("api/users")
    fun getList(
        @Query("page") page : Int
    ): Call<FollowerResponseDTO>
}