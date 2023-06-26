package org.android.go.sopt.data.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.android.go.sopt.data.remote.AddResponseDTO
import org.android.go.sopt.data.remote.ListResponseDTO
import retrofit2.Call
import retrofit2.http.*

interface AlbumService {

    @Multipart
    @POST("music")
    fun uploadMusic(
        @Header("id") id: String,
        @Part("title") title: RequestBody,
        @Part("singer") singer: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<AddResponseDTO>

    @GET("{id}/music")
    fun downloadMusic(
        @Path("id") id: String
    ): Call<ListResponseDTO>
}