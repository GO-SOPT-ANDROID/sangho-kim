package org.android.go.sopt.presentation.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivityLoginBinding
import org.android.go.sopt.presentation.main.MainActivity
import org.android.go.sopt.util.extension.makeSnackBar
import org.android.go.sopt.util.extension.makeToast
import timber.log.Timber

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 야간모드 무시
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // 자동로그인 위한 객체 생성
        sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        // 회원가입 버튼 클릭 시 이동
        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // 로그인 버튼 클릭 시 이동
        binding.btnLogin.setOnClickListener {
            loginWithServer()
        }

        // 뷰모델 observer 설정
        viewModel.loginResult.observe(this) { loginResult ->
            binding.root.makeToast(getString(R.string.toast_login_success))

            val idFromServer = loginResult.data.id
            editor.putString("id", idFromServer)
            editor.apply()

            val intent = Intent(binding.root.context, MainActivity::class.java)
            startActivityWithFlags(intent)
        }
        viewModel.errorResult.observe(this) { errorResult ->
            Timber.d("서버 통신 실패 : $errorResult")
            binding.root.makeSnackBar(getString(R.string.snackbar_server_failure))
        }

        // 화면 터치로 키보드 내리기
        binding.root.setOnClickListener {
            hideKeyboard(this)
        }

        // 자동로그인 설정
        autologin()
    }

    private fun loginWithServer() {
        viewModel.login(
            binding.etLoginId.text.toString(),
            binding.etLoginPw.text.toString()
        )
    }

    private fun autologin() {
        val idShared = sharedPreferences.getString("id", null)
        if (idShared != null) {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("id", idShared)
            }
            binding.root.makeToast(getString(R.string.toast_login_autologin))
            startActivityWithFlags(intent)
        }
    }

    private fun startActivityWithFlags(intent: Intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun hideKeyboard(activity: Activity) {
        val keyboard = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.hideSoftInputFromWindow(activity.window.decorView.applicationWindowToken, 0)
    }
}