package org.android.go.sopt.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import org.android.go.sopt.R
import org.android.go.sopt.account.AccountFragment
import org.android.go.sopt.databinding.ActivityPlaylistBinding
import org.android.go.sopt.playlist.ListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlaylistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 시작 화면 ListFragment로 설정
        supportFragmentManager.findFragmentById(R.id.fcv_main)
            ?: supportFragmentManager.beginTransaction().add(R.id.fcv_main, ListFragment()).commit()

        // 바텀 네비게이션뷰 구현
        binding.bnvMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_playlist -> changeFragment(ListFragment())
                R.id.menu_main -> changeFragment(MainFragment())
                R.id.menu_account -> changeFragment(AccountFragment())
            }
            true
        }

        // 리스트 화면에서 바텀 네비게이션뷰 두번 클릭 시 맨 위 화면으로
        binding.bnvMain.setOnItemReselectedListener { item ->
            if (item.itemId == R.id.menu_playlist) {
                when (val currentFragment =
                    supportFragmentManager.findFragmentById(R.id.fcv_main)) {
                    is ListFragment -> {
                        currentFragment.scrollToTop()
                    }
                }
            }
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fcv_main, fragment).commit()
    }
}