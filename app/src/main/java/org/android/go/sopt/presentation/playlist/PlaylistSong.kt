package org.android.go.sopt.presentation.playlist

import androidx.annotation.DrawableRes

data class PlaylistSong(
    val id: Int,
    @DrawableRes val album: Int,
    val artist: String,
    val title: String
)