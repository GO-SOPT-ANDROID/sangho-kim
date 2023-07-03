package org.android.go.sopt.data.service

import org.android.go.sopt.data.entity.remote.LoginRequestDTO
import org.android.go.sopt.data.entity.remote.LoginResponseDTO
import org.android.go.sopt.data.entity.remote.SignUpRequestDTO
import org.android.go.sopt.data.entity.remote.SignUpResponseDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("sign-up")
    suspend fun postSignUp(
        @Body request: SignUpRequestDTO,
    ): SignUpResponseDTO

    @POST("sign-in")
    suspend fun postLogin(
        @Body request: LoginRequestDTO,
    ): LoginResponseDTO
}