package org.android.go.sopt.presentation.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.android.go.sopt.data.entity.remote.ListResponseDTO
import org.android.go.sopt.di.AlbumServicePool.albumService

class PlaylistViewModel : ViewModel() {
    private val _playlistResult: MutableLiveData<ListResponseDTO> = MutableLiveData()
    val playlistResult: LiveData<ListResponseDTO> = _playlistResult

    private val _errorResult: MutableLiveData<String> = MutableLiveData()
    val errorResult: LiveData<String> = _errorResult

    fun addListFromServer() {
        viewModelScope.launch {
            runCatching {
                albumService.getMusic(
                    "qwqw12"
                )
            }.onSuccess {
                _playlistResult.value = it
            }.onFailure {
                _errorResult.value = it.message
            }
        }
    }
}