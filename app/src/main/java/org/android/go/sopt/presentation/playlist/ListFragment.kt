package org.android.go.sopt.presentation.playlist

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ConcatAdapter
import org.android.go.sopt.R
import org.android.go.sopt.data.local.mockPlayList
import org.android.go.sopt.data.local.mockPlayListTitle
import org.android.go.sopt.databinding.FragmentListBinding
import org.android.go.sopt.util.base.BindingFragment

class ListFragment : BindingFragment<FragmentListBinding>(R.layout.fragment_list) {

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

    fun scrollToTop() {
        binding.rvPlaylist.smoothScrollToPosition(0)
    }
}