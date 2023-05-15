package org.android.go.sopt.presentation.follower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentFollowerBinding
import org.android.go.sopt.remote.follower.FollowerResponseDTO
import org.android.go.sopt.remote.follower.FollowerServicePool.followerService
import org.android.go.sopt.util.makeSnackBar
import retrofit2.Call
import retrofit2.Response

class FollowerFragment : Fragment() {
    private var _binding: FragmentFollowerBinding? = null
    private val binding: FragmentFollowerBinding
        get() = requireNotNull(_binding) { "${this::class.java.simpleName}에서 에러가 발생했습니다." }

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

        // 서버 통신으로 User 리스트 받아오기
        addListFromServer(1)
        addListFromServer(2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addListFromServer(page: Int) {
        followerService.getList(page)
            .enqueue(object : retrofit2.Callback<FollowerResponseDTO> {
            override fun onResponse(
                call: Call<FollowerResponseDTO>,
                response: Response<FollowerResponseDTO>
            ) {
                if (response.isSuccessful) {
                    val responseList = response.body()?.data?.toMutableList()
                    responseList?.let {
                        followerList.addAll(responseList)
                    }
                    followerAdapter.submitList(followerList.toList())
                } else {
                    binding.root.makeSnackBar(getString(R.string.snackbar_signup_failure))
                }
            }
            override fun onFailure(call: Call<FollowerResponseDTO>, t: Throwable) {
                binding.root.makeSnackBar(getString(R.string.snackbar_signup_failure))
            }
        })
    }
}