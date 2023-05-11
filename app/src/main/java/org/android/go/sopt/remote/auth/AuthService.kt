package org.android.go.sopt.remote.auth

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("sign-up")
    fun signUp(
        @Body request: SignUpRequestDTO,
    ): Call<SignUpResponseDTO>

    @POST("sign-in")
    fun login(
        @Body request: LoginRequestDTO,
    ): Call<LoginResponseDTO>
}