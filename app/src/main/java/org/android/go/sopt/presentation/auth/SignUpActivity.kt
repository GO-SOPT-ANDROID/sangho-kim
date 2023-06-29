package org.android.go.sopt.presentation.auth

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.util.KeyboardVisibilityUtils
import org.android.go.sopt.util.base.BindingActivity
import org.android.go.sopt.util.extension.makeSnackBar
import org.android.go.sopt.util.extension.setOnSingleClickListener
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

        // SignUp 버튼 클릭
        binding.btnSignUp.setOnSingleClickListener {
            viewModel.signUp()
            observeSignUpResult()
            observeSignUpError()
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
    }

    private fun observeSignUpError() {
        viewModel.errorResult.observe(this) { errorResult ->
            Timber.d("서버 통신 실패 : $errorResult")
            binding.root.makeSnackBar(getString(R.string.snackbar_server_failure))
        }
    }

    private fun setKeyboardHeight() {
        keyboardVisibilityUtils =
            KeyboardVisibilityUtils(window, onShowKeyboard = { keyboardHeight ->
                binding.svSignUp.run {
                    smoothScrollTo(scrollX, scrollY + keyboardHeight)
                }
            })
    }

    // 키보드 바깥을 누르면 키보드 숨김 & 포커스 해제
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val focusView: View? = currentFocus
        if (focusView != null) {
            val rect = Rect()
            focusView.getGlobalVisibleRect(rect)
            val x = ev.x.toInt()
            val y = ev.y.toInt()
            if (!rect.contains(x, y)) {
                val imm: InputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(focusView.windowToken, 0)
                focusView.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}