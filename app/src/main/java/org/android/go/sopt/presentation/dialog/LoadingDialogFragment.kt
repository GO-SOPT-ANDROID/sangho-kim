package org.android.go.sopt.presentation.dialog

import android.os.Bundle
import android.view.View
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentLoadingDialogBinding
import org.android.go.sopt.util.base.BindingDialogFragment

class LoadingDialogFragment :
    BindingDialogFragment<FragmentLoadingDialogBinding>(R.layout.fragment_loading_dialog) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.setCancelable(false)
    }
}