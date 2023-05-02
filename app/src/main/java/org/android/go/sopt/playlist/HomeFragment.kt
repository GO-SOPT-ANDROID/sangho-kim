package org.android.go.sopt.playlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import org.android.go.sopt.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "${this::class.java.simpleName}에서 에러가 발생했습니다." }

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

        val playlistTitleAdapter = PlaylistTitleAdapter()
        playlistTitleAdapter.submitList(mockPlayListTitle)

        val playlistAdapter = PlaylistAdapter()
        playlistAdapter.submitList(mockPlayList)

        val adapter = ConcatAdapter(playlistTitleAdapter, playlistAdapter)
        binding.rvPlaylist.adapter = adapter

        var updatedList = mockPlayList

        binding.btnItemDelete.setOnClickListener {
            updatedList = updatedList.filterNot {
                it.id in playlistAdapter.selectionList
            }
            playlistAdapter.submitList(updatedList)
            playlistAdapter.selectionList = mutableListOf<Int>()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun scrollToTop() {
        binding.rvPlaylist.smoothScrollToPosition(0)
    }
}