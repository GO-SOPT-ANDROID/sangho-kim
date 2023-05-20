package org.android.go.sopt.presentation.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.remote.LoginRequestDTO
import org.android.go.sopt.data.remote.LoginResponseDTO
import org.android.go.sopt.module.AuthServicePool.authService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class LoginViewModel : ViewModel() {
    val loginResult: MutableLiveData<LoginResponseDTO> = MutableLiveData()

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
                    loginResult.value = response.body()
                } else {
                    Timber.d("서버 통신 실패")
                }
            }

            override fun onFailure(call: Call<LoginResponseDTO>, t: Throwable) {
                Timber.d("서버 통신 실패")
            }
        })
    }

}
