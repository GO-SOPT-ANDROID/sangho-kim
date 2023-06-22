package org.android.go.sopt.data.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface AlbumService {

    @Multipart
    @POST("upload")
    fun postImage(
        @PartMap body: HashMap<String, RequestBody>,
        @Part file: MultipartBody.Part?
    ): Call<Unit>
}