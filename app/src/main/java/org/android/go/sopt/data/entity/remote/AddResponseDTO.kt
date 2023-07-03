package org.android.go.sopt.data.entity.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddResponseDTO(

    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: MusicData,
) {
    @Serializable
    data class MusicData(

        @SerialName("title")
        val title: String,
        @SerialName("singer")
        val singer: String,
        @SerialName("url")
        val url: String,
    )
}
