package org.android.go.sopt.presentation.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.util.KeyboardVisibilityUtils
import org.android.go.sopt.util.makeSnackBar

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SignUp 버튼 클릭
        binding.btnSignUp.setOnClickListener {
            signInByRule()
        }

        // 화면 터치로 키보드 내리기
        binding.root.setOnClickListener {
            hideKeyboard(this)
        }

        // 받아온 클래스 활용해 스크롤뷰에 적용
        keyboardVisibilityUtils = KeyboardVisibilityUtils(window,
            onShowKeyboard = { keyboardHeight ->
                binding.svSignUp.run {
                    smoothScrollTo(scrollX, scrollY + keyboardHeight)
                }
            })
    }

    private fun hideKeyboard(activity: Activity) {
        val keyboard = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.hideSoftInputFromWindow(activity.window.decorView.applicationWindowToken, 0)
    }

    private fun signInByRule() {
        // 로그인 조건 설정 후 가입 마무리
        val id = binding.etSignUpId.text.toString()
        val pw = binding.etSignUpPw.text.toString()
        val name = binding.etSignUpName.text.toString()
        val skill = binding.etSignUpSkill.text.toString()

        if (canUserSignIn()) {
            // 반환할 인텐트 설정
            val intent = Intent(this, SignUpActivity::class.java).apply {
                putExtra("id", id)
                putExtra("pw", pw)
                putExtra("name", name)
                putExtra("skill", skill)
            }
            setResult(RESULT_OK, intent)
            finish()

        } else {
            binding.root.makeSnackBar(getString(R.string.snackbar_signup_rule))
        }
    }

    private fun canUserSignIn(): Boolean {
        return binding.etSignUpId.text.length in 6..10
                && binding.etSignUpPw.text.length in 8..12
                && binding.etSignUpName.text.isNotBlank()
                && binding.etSignUpSkill.text.isNotBlank()
    }
}