package org.android.go.sopt.presentation.add

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.android.go.sopt.module.AlbumServicePool.albumService
import org.android.go.sopt.util.ContentUriRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


class AddViewModel: ViewModel() {
    private val _image = MutableLiveData<ContentUriRequestBody>()
    val image: LiveData<ContentUriRequestBody>
        get() = _image

    fun setRequestBody(requestBody: ContentUriRequestBody) {
        _image.value = requestBody
    }

    fun uploadMusic(title: String, artist: String) {
        if (image.value == null) {
            Timber.e("아직 사진이 등록되지 않았습니다.")
        } else {
            val body = hashMapOf(
                "title" to title.toRequestBody("text/plain".toMediaType()),
                "artist" to artist.toRequestBody("text/plain".toMediaType())
            )
            val imageBody = image.value!!.toFormData()
            albumService.postImage(body, imageBody).enqueue(
                object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.isSuccessful)
                            Log.e("junseo", "success !!!")
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {

                    }

                }
            )
        }
    }
}