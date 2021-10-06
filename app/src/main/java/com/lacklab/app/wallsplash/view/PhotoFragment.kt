package com.lacklab.app.wallsplash.view

import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.base.BaseFragment
import com.lacklab.app.wallsplash.databinding.FragmentPhotoBinding

class PhotoFragment : BaseFragment<FragmentPhotoBinding>() {
    private val args: PhotoFragmentArgs by navArgs()
    override fun layout() = R.layout.fragment_photo

    override fun init() {
        initView()
    }

    private fun initView() {
        val url = args.photo.urls.regular
        Glide.with(binding.root)
            .load(url)
            .override(Target.SIZE_ORIGINAL)
            .into(binding.imageViewPhoto)
    }
}