package org.android.go.sopt.presentation.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.remote.ListResponseDTO
import org.android.go.sopt.module.AlbumServicePool.albumService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaylistViewModel : ViewModel() {
    private val _playlistResult: MutableLiveData<ListResponseDTO> = MutableLiveData()
    val playlistResult: LiveData<ListResponseDTO> = _playlistResult

    private val _errorResult: MutableLiveData<String> = MutableLiveData()
    val errorResult: LiveData<String> = _errorResult

    fun addListFromServer() {
        albumService.downloadMusic("qwqw12")
            .enqueue(object : Callback<ListResponseDTO> {
                override fun onResponse(
                    call: Call<ListResponseDTO>,
                    response: Response<ListResponseDTO>
                ) {
                    if (response.isSuccessful) {
                        _playlistResult.value = response.body()
                    } else {
                        _errorResult.value = response.message()
                    }
                }

                override fun onFailure(call: Call<ListResponseDTO>, t: Throwable) {
                    _errorResult.value = t.toString()
                }
            })
    }
}