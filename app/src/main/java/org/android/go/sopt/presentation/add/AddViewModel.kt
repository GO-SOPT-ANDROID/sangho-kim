package org.android.go.sopt.presentation.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.android.go.sopt.data.remote.AddResponseDTO
import org.android.go.sopt.module.AlbumServicePool.albumService
import org.android.go.sopt.util.ContentUriRequestBody


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

        viewModelScope.launch {
            runCatching {
                albumService.postMusic(
                    id, titleBody, singerBody, imageBody
                )
            }.onSuccess {
                _addResult.value = it
            }.onFailure {
                _errorResult.value = it.message
            }
        }
    }
}
