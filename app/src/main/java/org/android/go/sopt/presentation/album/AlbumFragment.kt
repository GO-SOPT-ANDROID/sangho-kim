package org.android.go.sopt.presentation.album

import android.os.Bundle
import android.view.View
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentAlbumBinding
import org.android.go.sopt.util.base.BindingFragment

class AlbumFragment : BindingFragment<FragmentAlbumBinding>(R.layout.fragment_album) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 앨범 리스트 설정
        binding.pagerAlbum.adapter = AlbumAdapter().apply {
            submitList(mockAlbumList)
        }
        // dots indicator 연결
        binding.albumDotsIndicator.setViewPager2(binding.pagerAlbum)
    }
}