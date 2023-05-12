package org.android.go.sopt.remote.follower

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FollowerService {

    @GET("api/users")
    fun getList(
        @Query("page") page : Int
    ): Call<FollowerResponseDTO>
}