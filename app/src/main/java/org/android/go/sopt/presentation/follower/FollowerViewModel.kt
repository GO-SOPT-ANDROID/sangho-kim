package org.android.go.sopt.presentation.follower

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.android.go.sopt.domain.model.FollowerModel
import org.android.go.sopt.domain.repository.FollowerRepository
import javax.inject.Inject

@HiltViewModel
class FollowerViewModel @Inject constructor(
    private val followerRepository: FollowerRepository
) : ViewModel() {

    private val _followerResult: MutableLiveData<List<FollowerModel>> = MutableLiveData()
    val followerResult: LiveData<List<FollowerModel>> = _followerResult

    private val _errorResult: MutableLiveData<String> = MutableLiveData()
    val errorResult: LiveData<String> = _errorResult

    fun addListFromServer() {
        viewModelScope.launch {
            runCatching {
                followerRepository.getData()
            }.onSuccess {
                _followerResult.value = it
            }.onFailure {
                _errorResult.value = it.message
            }
        }
    }
}