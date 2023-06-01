package org.android.go.sopt.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.remote.SignUpRequestDTO
import org.android.go.sopt.data.remote.SignUpResponseDTO
import org.android.go.sopt.module.AuthServicePool
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {
    private val _signUpResult: MutableLiveData<SignUpResponseDTO> = MutableLiveData()
    val signUpResult: LiveData<SignUpResponseDTO> = _signUpResult

    private val _errorResult: MutableLiveData<String> = MutableLiveData()
    val errorResult: LiveData<String> = _errorResult

    val idText: MutableLiveData<String> = MutableLiveData("")
    val pwText: MutableLiveData<String> = MutableLiveData("")
    val nameText: MutableLiveData<String> = MutableLiveData("")
    val skillText: MutableLiveData<String> = MutableLiveData("")

    fun signUp(id: String, password: String, name: String, skill: String) {
        AuthServicePool.authService.signUp(
            SignUpRequestDTO(
                id, password, name, skill
            )
        ).enqueue(object : Callback<SignUpResponseDTO> {
            override fun onResponse(
                call: Call<SignUpResponseDTO>, response: Response<SignUpResponseDTO>
            ) {
                if (response.isSuccessful) {
                    _signUpResult.value = response.body()
                } else {
                    _errorResult.value = response.message()
                }
            }

            override fun onFailure(call: Call<SignUpResponseDTO>, t: Throwable) {
                _errorResult.value = t.toString()
            }
        })
    }
}