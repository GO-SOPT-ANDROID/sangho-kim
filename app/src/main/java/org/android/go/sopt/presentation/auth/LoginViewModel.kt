package org.android.go.sopt.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.remote.LoginRequestDTO
import org.android.go.sopt.data.remote.LoginResponseDTO
import org.android.go.sopt.module.AuthServicePool.authService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val _loginResult: MutableLiveData<LoginResponseDTO> = MutableLiveData()
    val loginResult: LiveData<LoginResponseDTO> = _loginResult

    private val _errorResult: MutableLiveData<String> = MutableLiveData()
    val errorResult: LiveData<String> = _errorResult

    fun login(id: String, password: String) {
        authService.login(
            LoginRequestDTO(
                id,
                password
            )
        ).enqueue(object : Callback<LoginResponseDTO> {
            override fun onResponse(
                call: Call<LoginResponseDTO>,
                response: Response<LoginResponseDTO>
            ) {
                if (response.isSuccessful) {
                    _loginResult.value = response.body()
                } else {
                    _errorResult.value = response.message()
                }
            }

            override fun onFailure(call: Call<LoginResponseDTO>, t: Throwable) {
                _errorResult.value = t.toString()
            }
        })
    }
}
