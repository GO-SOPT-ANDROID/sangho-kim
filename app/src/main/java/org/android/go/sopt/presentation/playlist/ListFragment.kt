package org.android.go.sopt.presentation.playlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import org.android.go.sopt.R
import org.android.go.sopt.data.local.mockPlayListTitle
import org.android.go.sopt.databinding.FragmentListBinding
import org.android.go.sopt.util.base.BindingFragment
import org.android.go.sopt.util.extension.makeSnackBar
import org.android.go.sopt.util.extension.setOnSingleClickListener
import timber.log.Timber

class ListFragment : BindingFragment<FragmentListBinding>(R.layout.fragment_list) {

    private val viewModel by viewModels<PlaylistViewModel>()
    private val playlistTitleAdapter = PlaylistTitleAdapter()
    private val playlistAdapter = PlaylistAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.addListFromServer()
        observePlaylistResult()
        observePlaylistError()

        binding.btnItemDelete.setOnSingleClickListener {
            // 삭제 API 있으면 여기다 적용
        }
    }

    private fun observePlaylistResult() {
        viewModel.playlistResult.observe(viewLifecycleOwner) { playlistResult ->
            val responseList = playlistResult.data.musicList.toMutableList()
            playlistAdapter.submitList(responseList)
            playlistTitleAdapter.submitList(mockPlayListTitle)
            val adapter = ConcatAdapter(playlistTitleAdapter, playlistAdapter)
            binding.rvPlaylist.adapter = adapter
        }
    }

    private fun observePlaylistError() {
        viewModel.errorResult.observe(viewLifecycleOwner) { errorResult ->
            Timber.d("서버 통신 실패 : $errorResult")
            binding.root.makeSnackBar(getString(R.string.snackbar_server_failure))
        }
    }

    fun scrollToTop() {
        binding.rvPlaylist.smoothScrollToPosition(0)
    }
}