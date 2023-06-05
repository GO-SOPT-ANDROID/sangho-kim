package org.android.go.sopt.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.remote.SignUpRequestDTO
import org.android.go.sopt.data.remote.SignUpResponseDTO
import org.android.go.sopt.module.AuthServicePool
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class SignUpViewModel : ViewModel() {
    private val _signUpResult: MutableLiveData<SignUpResponseDTO> = MutableLiveData()
    val signUpResult: LiveData<SignUpResponseDTO> = _signUpResult

    private val _errorResult: MutableLiveData<String> = MutableLiveData()
    val errorResult: LiveData<String> = _errorResult

    val idText: MutableLiveData<String> = MutableLiveData("")
    val pwText: MutableLiveData<String> = MutableLiveData("")
    val nameText: MutableLiveData<String> = MutableLiveData("")
    val skillText: MutableLiveData<String> = MutableLiveData("")

    val isIdValid: LiveData<Boolean> = Transformations.map(idText) { checkIdValid(it) }
    val isPwValid: LiveData<Boolean> = Transformations.map(pwText) { checkPwValid(it) }

    val isButtonValid: MutableLiveData<Boolean> = MutableLiveData(false)

    fun signUp() {
        AuthServicePool.authService.signUp(
            SignUpRequestDTO(
                idText.toString(), pwText.toString(), nameText.toString(), skillText.toString()
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

    private fun checkIdValid(id: String): Boolean {
        if (id == "") return true
        val pattern = Pattern.compile(ID_PATTERN)
        val matcher = pattern.matcher(id)
        return matcher.matches()
    }

    private fun checkPwValid(pw: String): Boolean {
        if (pw == "") return true
        val pattern = Pattern.compile(PW_PATTERN)
        val matcher = pattern.matcher(pw)
        return matcher.matches()
    }

    fun checkButtonValid() {
        isButtonValid.value =
            (isIdValid.value == true && isPwValid.value == true && idText.value!!.isNotBlank() && pwText.value!!.isNotBlank())
    }

    companion object {
        const val ID_PATTERN = """^(?=.*[a-zA-Z])(?=.*\d).{6,10}$"""
        const val PW_PATTERN = """^(?=.*[a-zA-Z])(?=.*\d)(?=.*[~!@#$%^&*()?]).{6,12}$"""
    }
}