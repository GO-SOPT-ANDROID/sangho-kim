package org.android.go.sopt.presentation.add

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import coil.load
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentAddBinding
import org.android.go.sopt.presentation.playlist.ListFragment
import org.android.go.sopt.util.ContentUriRequestBody
import org.android.go.sopt.util.base.BindingFragment
import org.android.go.sopt.util.extension.makeToast
import timber.log.Timber

class AddFragment : BindingFragment<FragmentAddBinding>(R.layout.fragment_add) {

    private val viewModel by viewModels<AddViewModel>()

    private val launcher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { imageUri: Uri? ->
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
            if (viewModel.image.value != null) {
                viewModel.uploadMusic("aaaaa1")
                observeAddResult()
            } else {
                view.makeToast("앨범 이미지를 추가해주세요")
            }
        }
    }

    private fun observeAddResult() {
        viewModel.addResult.observe(viewLifecycleOwner) { addResult ->
            binding.root.makeToast(getString(R.string.snackbar_add_success))
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fcv_main, ListFragment())
            }
        }
        viewModel.errorResult.observe(viewLifecycleOwner) { errorResult ->
            Timber.d("서버 통신 실패 : $errorResult")
            binding.root.makeToast(getString(R.string.snackbar_server_failure))
        }
    }
}