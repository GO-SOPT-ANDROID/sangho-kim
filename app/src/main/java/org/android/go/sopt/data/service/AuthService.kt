package org.android.go.sopt.data.service

import org.android.go.sopt.data.remote.LoginRequestDTO
import org.android.go.sopt.data.remote.LoginResponseDTO
import org.android.go.sopt.data.remote.SignUpRequestDTO
import org.android.go.sopt.data.remote.SignUpResponseDTO
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