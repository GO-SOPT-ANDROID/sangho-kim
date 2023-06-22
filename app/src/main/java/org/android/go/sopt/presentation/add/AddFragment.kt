package org.android.go.sopt.presentation.add

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import coil.load
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentAddBinding
import org.android.go.sopt.util.base.BindingFragment

class AddFragment : BindingFragment<FragmentAddBinding>(R.layout.fragment_add) {

    private val launcher = registerForActivityResult(ActivityResultContracts.PickVisualMedia())
    { imageUri: Uri? ->
        binding.ivAddImage.load(imageUri)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddImage.setOnClickListener{
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }


    }

}