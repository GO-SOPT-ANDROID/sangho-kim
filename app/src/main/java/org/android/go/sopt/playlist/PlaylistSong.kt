package org.android.go.sopt.playlist

import androidx.annotation.DrawableRes

data class PlaylistSong(
    @DrawableRes val album: Int,
    val artist: String,
    val title: String
)