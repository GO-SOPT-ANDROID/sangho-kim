package org.android.go.sopt

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar
import org.android.go.sopt.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SignUp 버튼 클릭 시
        binding.btnSignUp.setOnClickListener {
            Log.d("sangho", "signup button clicked")

            val id = binding.etSignUpId.text.toString()
            val pw = binding.etSignUpPw.text.toString()
            val name = binding.etSignUpName.text.toString()
            val skill = binding.etSignUpSkill.text.toString()

            // 로그인 조건 설정 후 가입 마무리
            if (id.length in 6..10 && pw.length in 8..12) {
                Log.d("sangho", "able to signup")

                // 반환할 인텐트 설정
                val intent = Intent(this, SignUpActivity::class.java).apply {
                    putExtra("id", id)
                    putExtra("pw", pw)
                    putExtra("name", name)
                    putExtra("skill", skill)
                }

                Log.d("sangho", "$id, $pw, $name, $skill")
                setResult(RESULT_OK, intent)
                finish()

            } else {
                Log.d("sangho", "unable to signup")

                Snackbar.make(
                    binding.root,
                    "조건에 맞추어 작성해주세요.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
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

    private fun hideKeyboard(activity: Activity){
        val keyboard = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.hideSoftInputFromWindow(activity.window.decorView.applicationWindowToken, 0)
    }
}