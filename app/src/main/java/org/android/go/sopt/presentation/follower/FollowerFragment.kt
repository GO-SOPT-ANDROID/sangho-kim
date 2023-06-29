package org.android.go.sopt.presentation.follower

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentFollowerBinding
import org.android.go.sopt.data.remote.FollowerResponseDTO
import org.android.go.sopt.presentation.auth.LoginActivity
import org.android.go.sopt.presentation.dialog.LoadingDialogFragment
import org.android.go.sopt.util.base.BindingFragment
import org.android.go.sopt.util.extension.makeSnackBar
import org.android.go.sopt.util.extension.setOnSingleClickListener
import timber.log.Timber

class FollowerFragment : BindingFragment<FragmentFollowerBinding>(R.layout.fragment_follower) {

    private val viewModel by viewModels<FollowerViewModel>()
    private val followerList = mutableListOf<FollowerResponseDTO.User>()
    private val followerAdapter = FollowerAdapter()

    private lateinit var loadingDialogFragment: LoadingDialogFragment
    private lateinit var alertEventHandler : DialogInterface.OnClickListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFollower.adapter = followerAdapter

        // 로딩창 띄우기
        startLoadingDialog()

        // 뷰모델 observer 설정
        observeFollowerListResult()
        observeFollowerListError()

        // 서버 통신으로 User 리스트 받아오기
        viewModel.addListFromServer(1)
        viewModel.addListFromServer(2)

        // 로그아웃 다이얼로그 제어할 이벤트 핸들러 설정
        binding.btnLogout.setOnSingleClickListener {
            setAlertEventHandler()
            buildAlertDialog(alertEventHandler)
        }
    }

    private fun startLoadingDialog() {
        loadingDialogFragment = LoadingDialogFragment()
        parentFragmentManager.beginTransaction().add(R.id.fcv_main, loadingDialogFragment).commit()
    }

    private fun observeFollowerListResult() {
        viewModel.followerResult.observe(viewLifecycleOwner) { followerResult ->
            val responseList = followerResult.data.toMutableList()
            responseList.let {
                followerList.addAll(responseList)
            }
            followerAdapter.submitList(followerList.toList())

            // 두 페이지의 12명이 모두 들어올 때까지 보이도록 설정
            if (followerList.size == 12) {
                loadingDialogFragment.dismiss()
            }
        }
    }

    private fun observeFollowerListError() {
        viewModel.errorResult.observe(viewLifecycleOwner) { errorResult ->
            Timber.d("서버 통신 실패 : $errorResult")
            binding.root.makeSnackBar(getString(R.string.snackbar_server_failure))
        }
    }

    private fun setAlertEventHandler(): DialogInterface.OnClickListener {
        alertEventHandler = DialogInterface.OnClickListener { _, click ->
            if (click == DialogInterface.BUTTON_POSITIVE) {

                val sharedPreferences = context?.getSharedPreferences("loginInfo",
                    Context.MODE_PRIVATE
                )
                val editor = sharedPreferences?.edit()
                editor?.clear()
                editor?.apply()

                val intent = Intent(context, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
        return alertEventHandler
    }

    private fun buildAlertDialog(alertEventHandler : DialogInterface.OnClickListener) {
        AlertDialog.Builder(this.requireContext()).run {
            setTitle(resources.getString(R.string.dialog_account_logout_title))
            setMessage(resources.getString(R.string.dialog_account_logout_text))
            setPositiveButton("YES", alertEventHandler)
            setNegativeButton("NO", alertEventHandler)
            show()
        }
    }
}