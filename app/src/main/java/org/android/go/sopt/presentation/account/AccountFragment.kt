package org.android.go.sopt.presentation.account

import android.content.Context.MODE_PRIVATE
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentAccountBinding
import org.android.go.sopt.presentation.auth.LoginActivity
import org.android.go.sopt.util.base.BindingFragment

class AccountFragment : BindingFragment<FragmentAccountBinding>(R.layout.fragment_account) {

    private lateinit var alertEventHandler : DialogInterface.OnClickListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener {
            // 알림 다이얼로그 제어할 이벤트 핸들러 설정
            setAlertEventHandler()
            // 알림 다이얼로그 생성
            buildAlertDialog(alertEventHandler)
        }
    }

    private fun setAlertEventHandler(): DialogInterface.OnClickListener {
        alertEventHandler = DialogInterface.OnClickListener { _, click ->
            if (click == DialogInterface.BUTTON_POSITIVE) {

                val sharedPreferences = context?.getSharedPreferences("loginInfo", MODE_PRIVATE)
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