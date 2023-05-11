package org.android.go.sopt.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {

    @POST("sign-up")
    fun login(
        @Body request: SignUpRequestDTO,
    ): Call<SignUpResponseDTO>
}