package org.android.go.sopt.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.android.go.sopt.data.entity.remote.LoginRequestDTO
import org.android.go.sopt.data.entity.remote.LoginResponseDTO
import org.android.go.sopt.di.AuthServicePool.authService

class LoginViewModel : ViewModel() {
    private val _loginResult: MutableLiveData<LoginResponseDTO> = MutableLiveData()
    val loginResult: LiveData<LoginResponseDTO> = _loginResult

    private val _errorResult: MutableLiveData<String> = MutableLiveData()
    val errorResult: LiveData<String> = _errorResult

    fun login(id: String, password: String) {
        viewModelScope.launch {
            runCatching {
                authService.postLogin(
                    LoginRequestDTO(id, password)
                )
            }.onSuccess {
                _loginResult.value = it
            }.onFailure {
                _errorResult.value = it.message
            }
        }
    }
}
