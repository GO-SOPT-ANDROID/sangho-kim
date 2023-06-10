package org.android.go.sopt.presentation.follower

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.remote.FollowerResponseDTO
import org.android.go.sopt.module.FollowerServicePool
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {
    private val _followerResult: MutableLiveData<FollowerResponseDTO> = MutableLiveData()
    val followerResult: LiveData<FollowerResponseDTO> = _followerResult

    private val _errorResult: MutableLiveData<String> = MutableLiveData()
    val errorResult: LiveData<String> = _errorResult

    fun addListFromServer(page: Int) {
        FollowerServicePool.followerService.getList(page)
            .enqueue(object : Callback<FollowerResponseDTO> {
                override fun onResponse(
                    call: Call<FollowerResponseDTO>,
                    response: Response<FollowerResponseDTO>
                ) {
                    if (response.isSuccessful) {
                        _followerResult.value = response.body()
                    } else {
                        _errorResult.value = response.message()
                    }
                }

                override fun onFailure(call: Call<FollowerResponseDTO>, t: Throwable) {
                    _errorResult.value = t.toString()
                }
            })
    }
}