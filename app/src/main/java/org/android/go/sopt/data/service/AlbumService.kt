package org.android.go.sopt.data.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.android.go.sopt.data.entity.remote.AddResponseDTO
import org.android.go.sopt.data.entity.remote.ListResponseDTO
import retrofit2.http.*

interface AlbumService {

    @Multipart
    @POST("music")
    suspend fun postMusic(
        @Header("id") id: String,
        @Part("title") title: RequestBody,
        @Part("singer") singer: RequestBody,
        @Part image: MultipartBody.Part
    ): AddResponseDTO

    @GET("{id}/music")
    suspend fun getMusic(
        @Path("id") id: String
    ): ListResponseDTO
}