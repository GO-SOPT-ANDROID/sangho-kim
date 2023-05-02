package org.android.go.sopt.playlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivityPlaylistBinding

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
                R.id.menu_home -> changeFragment(HomeFragment())
                R.id.menu_search -> changeFragment(SearchFragment())
                R.id.menu_gallery -> changeFragment(GalleryFragment())
            }
            true
        }

        binding.bnvMain.setOnItemReselectedListener { item ->
            if (item.itemId == R.id.menu_home) {
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