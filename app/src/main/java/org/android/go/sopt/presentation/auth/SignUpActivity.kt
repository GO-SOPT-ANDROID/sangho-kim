package org.android.go.sopt.presentation.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
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

        // 텍스트 입력을 감지해서 조건 만족 시 버튼 활성화
        binding.etSignUpId.doAfterTextChanged {
            binding.btnSignUp.isEnabled = checkUserSignIn()
        }
        binding.etSignUpPw.doAfterTextChanged {
            binding.btnSignUp.isEnabled = checkUserSignIn()
        }
        binding.etSignUpName.doAfterTextChanged {
            binding.btnSignUp.isEnabled = checkUserSignIn()
        }
        binding.etSignUpSkill.doAfterTextChanged {
            binding.btnSignUp.isEnabled = checkUserSignIn()
        }

        // SignUp 버튼 클릭
        binding.btnSignUp.setOnClickListener {
            signUpWithServer()
        }

        // 뷰모델 observer 설정
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

        // 화면 터치로 키보드 내리기
        binding.root.setOnClickListener {
            hideKeyboard(this)
        }

        // 키보드 높이만큼 EditText 올려 버튼이 가리지 않도록 설정
        keyboardVisibilityUtils = KeyboardVisibilityUtils(window,
            onShowKeyboard = { keyboardHeight ->
                binding.svSignUp.run {
                    smoothScrollTo(scrollX, scrollY + keyboardHeight)
                }
            })
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

    private fun checkUserSignIn(): Boolean {
        return binding.etSignUpId.text.length in 6..10
                && binding.etSignUpPw.text.length in 8..12
                && binding.etSignUpName.text.isNotBlank()
                && binding.etSignUpSkill.text.isNotBlank()
    }
}