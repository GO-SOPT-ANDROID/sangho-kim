package org.android.go.sopt.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment() {
    private var _binding: FragmentAlbumBinding? = null
    private val binding: FragmentAlbumBinding
        get() = requireNotNull(_binding) { "${this::class.java.simpleName}에서 에러가 발생했습니다." }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pagerAlbum.adapter = AlbumAdapter().apply {
            submitList(mockAlbumList)
        }
        // dots indicator 연결
        binding.albumDotsIndicator.setViewPager2(binding.pagerAlbum)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}