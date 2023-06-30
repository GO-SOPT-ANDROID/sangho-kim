package org.android.go.sopt.presentation.main

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivityMainBinding
import org.android.go.sopt.presentation.add.AddFragment
import org.android.go.sopt.presentation.album.AlbumFragment
import org.android.go.sopt.presentation.follower.FollowerFragment
import org.android.go.sopt.presentation.playlist.ListFragment
import org.android.go.sopt.presentation.search.SearchFragment
import org.android.go.sopt.util.base.BindingActivity

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var listFragment: ListFragment
    private lateinit var albumFragment: AlbumFragment
    private lateinit var searchFragment: SearchFragment
    private lateinit var followerFragment: FollowerFragment
    private lateinit var addFragment: AddFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 시작 Fragment 설정
        setFirstFragment()

        // 바텀 네비게이션 뷰 구현
        changeFragmentByBnv()

        // 리스트 화면에서 바텀 네비게이션뷰 두번 클릭 시 맨 위 화면으로 이동
        binding.bnvMain.setOnItemReselectedListener {
            scrollToTopForReselectedItem(it)
        }
    }

    private fun setFirstFragment() {
        listFragment = ListFragment()
        supportFragmentManager.findFragmentById(R.id.fcv_main)
            ?: supportFragmentManager.beginTransaction().add(R.id.fcv_main, listFragment).commit()
    }

    // 바텀 네비게이션 누를 때마다 새로운 프래그먼트 객체가 생성되지 않도록 설정
    private fun changeFragmentByBnv() {
        binding.bnvMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_playlist -> changeFragment(listFragment)
                R.id.menu_album -> {
                    if (!::albumFragment.isInitialized) {
                        albumFragment = AlbumFragment()
                    }
                    changeFragment(albumFragment)
                }

                R.id.menu_add -> {
                    if (!::addFragment.isInitialized) {
                        addFragment = AddFragment()
                    }
                    changeFragment(AddFragment())
                }

                R.id.menu_search -> {
                    if (!::searchFragment.isInitialized) {
                        searchFragment = SearchFragment()
                    }
                    changeFragment(SearchFragment())
                }

                R.id.menu_follower -> {
                    if (!::followerFragment.isInitialized) {
                        followerFragment = FollowerFragment()
                    }
                    changeFragment(FollowerFragment())
                }
            }
            true
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fcv_main, fragment)
        }
    }

    private fun scrollToTopForReselectedItem(item: MenuItem) {
        if (item.itemId == R.id.menu_playlist) {
            when (val currentFragment = supportFragmentManager.findFragmentById(R.id.fcv_main)) {
                is ListFragment -> {
                    currentFragment.scrollToTop()
                }
            }
        }
    }
}