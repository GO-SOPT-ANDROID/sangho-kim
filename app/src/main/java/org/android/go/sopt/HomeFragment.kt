package org.android.go.sopt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.android.go.sopt.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "${this::class.java.simpleName}에서 에러가 발생했습니다." }

    private val mockPlayList = listOf<Song>(
        Song(
            album = R.drawable.ic_launcher_background,
            artist = "Lacuna",
            title = "Far Away",
            ),
        Song(
            album = R.drawable.ic_launcher_background,
            artist = "최유리",
            title = "동네",
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 대부분의 로직은 여기에 구현

        // requireContext : null 안되는 context로 반환
        val adapter = MyAdapter(requireContext())
        binding.rvPlaylist.adapter = adapter
        adapter.setItemList(mockPlayList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}