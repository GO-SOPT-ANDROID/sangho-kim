package org.android.go.sopt.playlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import org.android.go.sopt.R
import org.android.go.sopt.account.GalleryFragment
import org.android.go.sopt.databinding.ActivityPlaylistBinding
import org.android.go.sopt.main.SearchFragment

class PlaylistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlaylistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.findFragmentById(R.id.fcv_main)
            ?: supportFragmentManager.beginTransaction().add(R.id.fcv_main, HomeFragment()).commit()

        binding.bnvMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_playlist -> changeFragment(HomeFragment())
                R.id.menu_main -> changeFragment(SearchFragment())
                R.id.menu_account -> changeFragment(GalleryFragment())
            }
            true
        }

        binding.bnvMain.setOnItemReselectedListener { item ->
            if (item.itemId == R.id.menu_playlist) {
                when (val currentFragment =
                    supportFragmentManager.findFragmentById(R.id.fcv_main)) {
                    is HomeFragment -> {
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