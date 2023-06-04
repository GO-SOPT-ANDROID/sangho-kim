package org.android.go.sopt.presentation.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.util.KeyboardVisibilityUtils
import org.android.go.sopt.util.base.BindingActivity
import org.android.go.sopt.util.extension.makeSnackBar
import timber.log.Timber

class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {

    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils

    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.vm = viewModel
        binding.lifecycleOwner = this

        observeSignUpFormat()
        observeSignUpResult()

        // SignUp 버튼 클릭
        binding.btnSignUp.setOnClickListener {
            signUpWithServer()
        }

        // 화면 터치로 키보드 내리기
        binding.root.setOnClickListener {
            hideKeyboard(this)
        }

        // 키보드 높이만큼 EditText 올려 버튼이 가리지 않도록 설정
        setKeyboardHeight()
    }

    private fun observeSignUpFormat() {
        viewModel.isIdValid.observe(this) { isIdValid ->
            binding.layoutSignUpId.error = if (isIdValid) "" else "아이디 형식이 올바르지 않습니다."
            viewModel.checkButtonValid()
        }
        viewModel.isPwValid.observe(this) { isPwValid ->
            binding.layoutSignUpPw.error = if (isPwValid) "" else "비밀번호 형식이 올바르지 않습니다."
            viewModel.checkButtonValid()
        }
    }

    private fun observeSignUpResult() {
        viewModel.signUpResult.observe(this) { signUpResult ->
            binding.root.makeSnackBar(getString(R.string.snackbar_signup_success))
            if (!isFinishing) {
                val intent = Intent(binding.root.context, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        viewModel.errorResult.observe(this) { errorResult ->
            Timber.d("서버 통신 실패 : $errorResult")
            binding.root.makeSnackBar(getString(R.string.snackbar_server_failure))
        }
    }

    private fun signUpWithServer() {
        viewModel.signUp(
            binding.etSignUpId.text.toString(),
            binding.etSignUpPw.text.toString(),
            binding.etSignUpName.text.toString(),
            binding.etSignUpSkill.text.toString()
        )
    }

    private fun hideKeyboard(activity: Activity) {
        val keyboard = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.hideSoftInputFromWindow(activity.window.decorView.applicationWindowToken, 0)
    }

    private fun setKeyboardHeight() {
        keyboardVisibilityUtils =
            KeyboardVisibilityUtils(window, onShowKeyboard = { keyboardHeight ->
                binding.svSignUp.run {
                    smoothScrollTo(scrollX, scrollY + keyboardHeight)
                }
            })
    }
}