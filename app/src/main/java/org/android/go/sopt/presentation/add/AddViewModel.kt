package org.android.go.sopt.presentation.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.android.go.sopt.data.remote.AddResponseDTO
import org.android.go.sopt.module.AlbumServicePool.albumService
import org.android.go.sopt.util.ContentUriRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddViewModel : ViewModel() {
    private val _addResult: MutableLiveData<AddResponseDTO> = MutableLiveData()
    val addResult: LiveData<AddResponseDTO> = _addResult

    private val _errorResult: MutableLiveData<String> = MutableLiveData()
    val errorResult: LiveData<String> = _errorResult

    val titleText: MutableLiveData<String> = MutableLiveData("")
    val singerText: MutableLiveData<String> = MutableLiveData("")

    private val _image = MutableLiveData<ContentUriRequestBody>()
    val image: LiveData<ContentUriRequestBody>
        get() = _image

    fun setRequestBody(requestBody: ContentUriRequestBody) {
        _image.value = requestBody
    }

    fun uploadMusic(id: String) {
        val titleBody = titleText.value.toString().toRequestBody("text/plain".toMediaType())
        val singerBody = singerText.value.toString().toRequestBody("text/plain".toMediaType())
        val imageBody = image.value!!.toFormData()

        albumService.uploadMusic(id, titleBody, singerBody, imageBody)
            .enqueue(object : Callback<AddResponseDTO> {
                override fun onResponse(
                    call: Call<AddResponseDTO>, response: Response<AddResponseDTO>
                ) {
                    if (response.isSuccessful) {
                        _addResult.value = response.body()
                    } else {
                        _errorResult.value = response.message()
                    }
                }

                override fun onFailure(call: Call<AddResponseDTO>, t: Throwable) {
                    _errorResult.value = t.toString()
                }
            })
    }
}