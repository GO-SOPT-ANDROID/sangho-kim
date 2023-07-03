package org.android.go.sopt.data.entity.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDTO(
    @SerialName("id")
    val id: String,
    @SerialName("password")
    val password: String,
)