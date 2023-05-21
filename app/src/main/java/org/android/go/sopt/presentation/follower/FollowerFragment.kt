package org.android.go.sopt.presentation.follower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentFollowerBinding
import org.android.go.sopt.data.remote.FollowerResponseDTO
import org.android.go.sopt.util.extension.makeSnackBar
import timber.log.Timber

class FollowerFragment : Fragment() {
    private var _binding: FragmentFollowerBinding? = null
    private val binding: FragmentFollowerBinding
        get() = requireNotNull(_binding) { "${this::class.java.simpleName}에서 에러가 발생했습니다." }

    private val viewModel by viewModels<FollowerViewModel>()

    private val followerList = mutableListOf<FollowerResponseDTO.User>()
    private val followerAdapter = FollowerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFollower.adapter = followerAdapter

        // 뷰모델 observer 설정
        viewModel.followerResult.observe(viewLifecycleOwner) {followerResult ->
            val responseList = followerList.toMutableList()
            responseList.let {
                followerList.addAll(responseList)
            }
            followerAdapter.submitList(followerList.toList())
        }
        viewModel.errorResult.observe(viewLifecycleOwner) { errorResult ->
            Timber.d("서버 통신 실패 : $errorResult")
            binding.root.makeSnackBar(getString(R.string.snackbar_server_failure))
        }

        // 서버 통신으로 User 리스트 받아오기
        viewModel.addListFromServer(1)
        viewModel.addListFromServer(2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}