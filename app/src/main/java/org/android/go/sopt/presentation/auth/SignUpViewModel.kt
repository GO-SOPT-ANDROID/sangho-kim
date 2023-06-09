package org.android.go.sopt.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.android.go.sopt.data.entity.remote.SignUpRequestDTO
import org.android.go.sopt.data.entity.remote.SignUpResponseDTO
import org.android.go.sopt.di.AuthServicePool.authService
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

    val isIdValid: LiveData<Boolean> = idText.map { id ->
        checkIdValid(id)
    }
    val isPwValid: LiveData<Boolean> = pwText.map { pw ->
        checkPwValid(pw)
    }

    val isButtonValid: MutableLiveData<Boolean> = MutableLiveData(false)

    fun signUp() {
        viewModelScope.launch {
            runCatching {
                authService.postSignUp(
                    SignUpRequestDTO(
                        idText.value.toString(),
                        pwText.value.toString(),
                        nameText.value.toString(),
                        skillText.value.toString()
                    )
                )
            }.onSuccess {
                _signUpResult.value = it
            }.onFailure {
                _errorResult.value = it.message
            }
        }
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
        private const val ID_PATTERN = """^(?=.*[a-zA-Z])(?=.*\d).{6,10}$"""
        private const val PW_PATTERN = """^(?=.*[a-zA-Z])(?=.*\d)(?=.*[~!@#$%^&*()?]).{6,12}$"""
    }
}