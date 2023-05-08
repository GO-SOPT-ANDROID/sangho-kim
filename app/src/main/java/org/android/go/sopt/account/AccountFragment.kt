package org.android.go.sopt.account

import android.content.Context.MODE_PRIVATE
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentAccountBinding
import org.android.go.sopt.login.LoginActivity

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding: FragmentAccountBinding
        get() = requireNotNull(_binding) { "${this::class.java.simpleName}에서 에러가 발생했습니다." }

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
            // 알림 제어할 이벤트핸들러 설정
            val alertEventHandler = DialogInterface.OnClickListener { _, click ->
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
            // 알림 빌더 생성
            AlertDialog.Builder(this.requireContext()).run {
                setTitle(resources.getString(R.string.dialog_logout_title))
                setMessage(resources.getString(R.string.dialog_logout_text))
                setPositiveButton("YES", alertEventHandler)
                setNegativeButton("NO", alertEventHandler)
                show()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}