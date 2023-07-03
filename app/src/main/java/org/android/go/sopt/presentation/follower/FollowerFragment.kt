package org.android.go.sopt.presentation.follower

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentFollowerBinding
import org.android.go.sopt.presentation.auth.LoginActivity
import org.android.go.sopt.presentation.dialog.LoadingDialogFragment
import org.android.go.sopt.util.base.BindingFragment
import org.android.go.sopt.util.extension.makeSnackBar
import org.android.go.sopt.util.extension.setOnSingleClickListener
import org.android.go.sopt.util.state.RemoteUiState.Failure
import org.android.go.sopt.util.state.RemoteUiState.Loading
import org.android.go.sopt.util.state.RemoteUiState.Success

@AndroidEntryPoint
class FollowerFragment : BindingFragment<FragmentFollowerBinding>(R.layout.fragment_follower) {

    private val viewModel by viewModels<FollowerViewModel>()
    private val followerAdapter = FollowerAdapter()
    private var loadingDialogFragment: LoadingDialogFragment = LoadingDialogFragment()
    private lateinit var alertEventHandler: DialogInterface.OnClickListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFollower.adapter = followerAdapter

        // 뷰모델 observer 설정 후 서버통신
        observeFollowerListState()
        viewModel.addListFromServer()

        // 로그아웃 다이얼로그 제어할 이벤트 핸들러 설정
        binding.btnLogout.setOnSingleClickListener {
            setAlertEventHandler()
            buildAlertDialog(alertEventHandler)
        }
    }

    private fun observeFollowerListState() {
        viewModel.followerState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Loading -> {
                    parentFragmentManager.beginTransaction()
                        .add(R.id.fcv_main, loadingDialogFragment).commit()
                }

                is Success -> {
                    followerAdapter.submitList(viewModel.followerResult.value)
                    dismissLoadingDialog()
                }

                is Failure -> {
                    dismissLoadingDialog()
                    binding.root.makeSnackBar(getString(R.string.snackbar_server_failure))
                }
            }
        }
    }

    private fun dismissLoadingDialog() {
        if (loadingDialogFragment.isAdded) loadingDialogFragment.dismiss()
    }

    private fun setAlertEventHandler(): DialogInterface.OnClickListener {
        alertEventHandler = DialogInterface.OnClickListener { _, click ->
            if (click == DialogInterface.BUTTON_POSITIVE) {

                val sharedPreferences = context?.getSharedPreferences(
                    "loginInfo",
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

    private fun buildAlertDialog(alertEventHandler: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this.requireContext()).run {
            setTitle(resources.getString(R.string.dialog_account_logout_title))
            setMessage(resources.getString(R.string.dialog_account_logout_text))
            setPositiveButton("YES", alertEventHandler)
            setNegativeButton("NO", alertEventHandler)
            show()
        }
    }
}