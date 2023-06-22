package org.android.go.sopt.presentation.auth

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivityLoginBinding
import org.android.go.sopt.presentation.main.MainActivity
import org.android.go.sopt.util.base.BindingActivity
import org.android.go.sopt.util.extension.makeSnackBar
import org.android.go.sopt.util.extension.makeToast
import timber.log.Timber

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 야간 모드 무시
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // 자동 로그인 위한 객체 생성
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
        observeLoginResult()
        observeLoginError()

        // 자동 로그인 설정
        autologin()
    }

    private fun loginWithServer() {
        viewModel.login(
            binding.etLoginId.text.toString(), binding.etLoginPw.text.toString()
        )
    }

    private fun observeLoginResult() {
        viewModel.loginResult.observe(this) { loginResult ->
            binding.root.makeToast(getString(R.string.toast_login_success))

            // 자동 로그인 위해 통신 데이터 저장
            val idFromServer = loginResult.data.id
            editor.putString("id", idFromServer)
            editor.apply()

            val intent = Intent(binding.root.context, MainActivity::class.java)
            startActivityWithFlags(intent)
        }
    }

    private fun observeLoginError() {
        viewModel.errorResult.observe(this) { errorResult ->
            Timber.d("서버 통신 실패 : $errorResult")
            binding.root.makeSnackBar(getString(R.string.snackbar_server_failure))
        }
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