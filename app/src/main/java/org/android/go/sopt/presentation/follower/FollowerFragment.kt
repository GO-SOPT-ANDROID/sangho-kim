package org.android.go.sopt.presentation.follower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kotlinx.coroutines.delay
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentFollowerBinding
import org.android.go.sopt.data.remote.FollowerResponseDTO
import org.android.go.sopt.util.base.BindingFragment
import org.android.go.sopt.util.extension.makeSnackBar
import timber.log.Timber

class FollowerFragment :  BindingFragment<FragmentFollowerBinding>(R.layout.fragment_follower){

    private val viewModel by viewModels<FollowerViewModel>()

    private val followerList = mutableListOf<FollowerResponseDTO.User>()
    private val followerAdapter = FollowerAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFollower.adapter = followerAdapter

        // 뷰모델 observer 설정
        viewModel.followerResult.observe(viewLifecycleOwner) {followerResult ->
            val responseList = followerResult.data.toMutableList()
            responseList.let {
                followerList.addAll(responseList)
            }
            followerAdapter.submitList(followerList.toList())

            // 두 페이지의 12명이 모두 들어올 때까지 보이도록 설정
            if (followerList.size == 12) {
                binding.progressBarFollower.visibility = View.GONE
            }
        }
        viewModel.errorResult.observe(viewLifecycleOwner) { errorResult ->
            Timber.d("서버 통신 실패 : $errorResult")
            binding.root.makeSnackBar(getString(R.string.snackbar_server_failure))
        }

        // 서버 통신으로 User 리스트 받아오기
        viewModel.addListFromServer(1)
        viewModel.addListFromServer(2)
    }
}