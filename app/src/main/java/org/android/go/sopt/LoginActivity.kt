package org.android.go.sopt

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.HIDE_IMPLICIT_ONLY
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import org.android.go.sopt.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var id: String
    private lateinit var pw: String
    private lateinit var name: String
    private lateinit var skill: String
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 자동로그인 위한 객체 생성
        sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        // 회원가입 액티비티에서 반환된 intent에서 결과값 받아옴
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    Log.d("sangho", "successful signup")
                    Snackbar.make(
                        binding.root,
                        "회원가입에 성공하였습니다.",
                        Snackbar.LENGTH_SHORT
                    ).show()

                    // 전달 받은 result 데이터의 String 가져옴
                    id = result.data?.getStringExtra("id") ?: ""
                    pw = result.data?.getStringExtra("pw") ?: ""
                    name = result.data?.getStringExtra("name") ?: ""
                    skill = result.data?.getStringExtra("skill") ?: ""

                    Log.d("sangho", "$id, $pw, $name, $skill")
                }
            }

        // 회원가입 버튼 클릭 시 이동
        binding.btnSignUp.setOnClickListener {
            Log.d("sangho", "signup button clicked")

            val intent = Intent(this, SignUpActivity::class.java)
            resultLauncher.launch(intent)
        }

        // 로그인 버튼 클릭 시 이동
        binding.btnLogin.setOnClickListener {
            Log.d("sangho", "login button clicked")

            val idEntered = binding.etLoginId.text.toString()
            val pwEntered = binding.etLoginPw.text.toString()

            // 로그인 조건 확인
            if (idEntered == id && pwEntered == pw) {
                Log.d("sangho", "able to login")

                val intent = Intent(this, MyPageActivity::class.java).apply {
                    putExtra("id", id)
                    putExtra("pw", pw)
                    putExtra("name", name)
                    putExtra("skill", skill)
                }
                Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()

                // 로그인 성공한 정보는 자동로그인을 위해 저장
                editor.putString("id", id)
                editor.putString("pw", pw)
                editor.apply()

                // 로그인 액티비티 종료 후 자기소개 화면으로 넘어가기
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()

            } else {
                Log.d("sangho", "unable to login")
                Snackbar.make(
                    binding.root,
                    "회원가입 정보와 일치하지 않습니다.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        // 화면 터치로 키보드 내리기
        binding.root.setOnClickListener {
            hideKeyboard(this)
        }

        // 자동로그인 설정
        val idShared = sharedPreferences.getString("id", null)
        val pwShared = sharedPreferences.getString("pw", null)
        if (idShared != null && pwShared != null) {
            Log.d("sangho", "sharedPreference -> id=$idShared, pw=$pwShared")
            val intent = Intent(this, MyPageActivity::class.java).apply {
                putExtra("id", idShared)
                putExtra("pw", pwShared)
            }
            Toast.makeText(this, "자동 로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun hideKeyboard(activity: Activity){
        val keyboard = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.hideSoftInputFromWindow(activity.window.decorView.applicationWindowToken, 0)
    }
}