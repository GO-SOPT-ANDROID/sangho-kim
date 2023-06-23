package org.android.go.sopt.presentation.add

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.ListFragment
import androidx.fragment.app.viewModels
import coil.load
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentAddBinding
import org.android.go.sopt.util.ContentUriRequestBody
import org.android.go.sopt.util.base.BindingFragment
import org.android.go.sopt.util.extension.makeSnackBar
import timber.log.Timber

class AddFragment : BindingFragment<FragmentAddBinding>(R.layout.fragment_add) {

    private val viewModel by viewModels<AddViewModel>()

    private val launcher = registerForActivityResult(ActivityResultContracts.PickVisualMedia())
    { imageUri: Uri? ->
        binding.ivAddImage.load(imageUri)
        viewModel.setRequestBody(ContentUriRequestBody(requireContext(), imageUri!!))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = this

        binding.btnAddImage.setOnClickListener {
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.btnAdd.setOnClickListener {
            viewModel.uploadMusic("aaaaa1")
            observeAddResult()
        }
    }

    private fun observeAddResult() {
        viewModel.addResult.observe(viewLifecycleOwner) { addResult ->
            binding.root.makeSnackBar(getString(R.string.snackbar_signup_success))
            parentFragmentManager.beginTransaction().replace(R.id.fcv_main, ListFragment()).commit()
        }
        viewModel.errorResult.observe(viewLifecycleOwner) { errorResult ->
            Timber.d("서버 통신 실패 : $errorResult")
            binding.root.makeSnackBar(getString(R.string.snackbar_server_failure))
        }
    }

}