package org.android.go.sopt.presentation.follower

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.android.go.sopt.domain.model.FollowerModel
import org.android.go.sopt.domain.repository.FollowerRepository
import org.android.go.sopt.util.state.RemoteUiState
import org.android.go.sopt.util.state.RemoteUiState.Failure
import org.android.go.sopt.util.state.RemoteUiState.Loading
import org.android.go.sopt.util.state.RemoteUiState.Success
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FollowerViewModel @Inject constructor(
    private val followerRepository: FollowerRepository
) : ViewModel() {

    private val _followerResult: MutableLiveData<List<FollowerModel>> = MutableLiveData()
    val followerResult: LiveData<List<FollowerModel>> = _followerResult

    private val _followerState = MutableLiveData<RemoteUiState>()
    val followerState: LiveData<RemoteUiState> = _followerState

    fun addListFromServer() {
        viewModelScope.launch {
            _followerState.value = Loading

            runCatching {
                followerRepository.getData()

            }.onSuccess { response ->
                if (response.isEmpty()) {
                    _followerState.value = Failure(null)
                    return@onSuccess
                }
                _followerResult.value = response
                _followerState.value = Success
                Timber.d("GET FOLLOWER LIST SUCCESS : $response")

            }.onFailure { t ->
                if (t is HttpException) {
                    _followerState.value = Failure(null)
                    Timber.e("GET FOLLOWER LIST FAIL : ${t.message}")
                }
            }
        }
    }
}