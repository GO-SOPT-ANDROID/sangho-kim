package org.android.go.sopt.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListResponseDTO(

    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: MusicList,
) {

    @Serializable
    data class MusicList(

        @SerialName("musicList")
        val musicList: List<MusicData>
    )
}

@Serializable
data class MusicData(

    @SerialName("title")
    val title: String,
    @SerialName("singer")
    val singer: String,
    @SerialName("url")
    val url: String,
)