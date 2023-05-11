package org.android.go.sopt.presentation.account

import android.content.Context.MODE_PRIVATE
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentAccountBinding
import org.android.go.sopt.presentation.auth.LoginActivity

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding: FragmentAccountBinding
        get() = requireNotNull(_binding) { "${this::class.java.simpleName}에서 에러가 발생했습니다." }

    private lateinit var alertEventHandler : DialogInterface.OnClickListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener {
            // 알림 다이얼로그 제어할 이벤트 핸들러 설정
            setAlertEventHandler()
            // 알림 다이얼로그 생성
            buildAlertDialog(alertEventHandler)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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