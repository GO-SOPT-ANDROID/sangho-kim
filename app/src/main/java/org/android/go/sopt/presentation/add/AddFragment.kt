package org.android.go.sopt.presentation.add

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentAddBinding
import org.android.go.sopt.util.ContentUriRequestBody
import org.android.go.sopt.util.base.BindingFragment

class AddFragment : BindingFragment<FragmentAddBinding>(R.layout.fragment_add) {

    private val viewModel by viewModels<AddViewModel>()

    private val launcher = registerForActivityResult(ActivityResultContracts.PickVisualMedia())
    { imageUri: Uri? ->
        viewModel.setRequestBody(ContentUriRequestBody(requireContext(), imageUri!!))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddImage.setOnClickListener{
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }


    }

}