package org.android.go.sopt.remote.follower

import retrofit2.Call
import retrofit2.http.GET

interface FollowerService {

    @GET("api/users")
    fun signUp(): Call<FollowerResponseDTO>
}