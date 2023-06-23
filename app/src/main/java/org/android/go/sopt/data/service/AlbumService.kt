package org.android.go.sopt.data.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface AlbumService {

    @Multipart
    @POST("music")
    fun uploadMusic(
        @Header("id") id: String,
        @Part body: HashMap<String, RequestBody>,
        @Part image: MultipartBody.Part?
    ): Call<Unit>
}