package org.android.go.sopt.data.entity.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.android.go.sopt.domain.model.FollowerModel

@Serializable
data class FollowerResponseDTO(
    @SerialName("page")
    val page: Int,
    @SerialName("per_page")
    val perPage: Int,
    @SerialName("total")
    val total: Int,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("data")
    val data: List<User>,
    @SerialName("support")
    val support: Support,
) {
    @Serializable
    data class User(
        @SerialName("id")
        val id: Int,
        @SerialName("email")
        val email: String,
        @SerialName("first_name")
        val firstName: String,
        @SerialName("last_name")
        val lastName: String,
        @SerialName("avatar")
        val avatar: String
    )

    @Serializable
    data class Support(
        @SerialName("url")
        val url: String,
        @SerialName("text")
        val text: String
    )

    fun FollowerResponseDTO.toFollowerModel() : List<FollowerModel> = data.map { user ->
        FollowerModel(
            id = user.id,
            name = "${user.firstName} ${user.lastName}",
            email = user.email,
            image = user.avatar
        )
    }
}