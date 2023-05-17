package org.android.go.sopt.data.local

import androidx.annotation.DrawableRes

data class PlaylistSong(
    val id: Int,
    @DrawableRes val album: Int,
    val artist: String,
    val title: String
)