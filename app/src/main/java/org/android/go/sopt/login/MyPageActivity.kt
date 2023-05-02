package org.android.go.sopt.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.android.go.sopt.databinding.ActivityMyPageBinding

class MyPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 받아온 인텐트로 텍스트 대치
        val name = intent.getStringExtra("name")
        binding.myPageName.text = "이름 : $name"
        val skill = intent.getStringExtra("skill")
        binding.myPageSkill.text = "특기 : $skill"

    }
}