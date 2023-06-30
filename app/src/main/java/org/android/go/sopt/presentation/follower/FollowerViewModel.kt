package org.android.go.sopt.presentation.follower

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.android.go.sopt.data.remote.FollowerResponseDTO
import org.android.go.sopt.module.FollowerServicePool.followerService

class FollowerViewModel : ViewModel() {
    private val _followerResult: MutableLiveData<FollowerResponseDTO> = MutableLiveData()
    val followerResult: LiveData<FollowerResponseDTO> = _followerResult

    private val _errorResult: MutableLiveData<String> = MutableLiveData()
    val errorResult: LiveData<String> = _errorResult

    fun addListFromServer(page: Int) {
        viewModelScope.launch {
            runCatching {
                followerService.getList(page)
            }.onSuccess {
                _followerResult.value = it
            }.onFailure {
                _errorResult.value = it.message
            }
        }
    }
}