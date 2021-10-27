package com.lacklab.app.wallsplash.ui.view

import android.os.Bundle
import android.view.View
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.base.BaseFragment
import com.lacklab.app.wallsplash.data.UnsplashPhoto
import com.lacklab.app.wallsplash.databinding.FragmentPhotoBinding

class PhotoFragment : BaseFragment<FragmentPhotoBinding>() {
//    private val args: PhotoFragmentArgs by navArgs()
    private var photoItemBundle: Bundle? = null
    private var photo: UnsplashPhoto? = null
    override fun layout() = R.layout.fragment_photo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater
                .from(context)
                .inflateTransition(android.R.transition.move)
    }

    override fun init() {
        initView()
        bindEvents()
    }

    override fun clear() {
        photoItemBundle = null
        photo = null
    }

    override fun clearView() {
        binding = null
    }

    private fun initView() {
        photoItemBundle = requireActivity().intent.getBundleExtra("photoItemBundle")!!
        photo = photoItemBundle!!.getParcelable<UnsplashPhoto>("photoItem")
        with(binding!!) {
            photoItem = photo
            Glide.with(root)
                .load(photo!!.urls!!.regular)
                .override(SIZE_ORIGINAL)
//            .addListener(object: RequestListener<Drawable>{
//                override fun onLoadFailed(
//                    e: GlideException?,
//                    model: Any?,
//                    target: Target<Drawable>?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    startPostponedEnterTransition()
//                    return false
//                }
//
//                override fun onResourceReady(
//                    resource: Drawable?,
//                    model: Any?,
//                    target: Target<Drawable>?,
//                    dataSource: DataSource?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    startPostponedEnterTransition()
//                    return false
//                }
//
//            })
                .into(imageViewPhoto)
        }

    }

    private fun bindEvents() {
        with(binding!!) {
            // set toolbar action
            toolbarTop.setNavigationOnClickListener {
                requireActivity().onBackPressed();
            }

            // set info action
            imageViewInfo.setOnClickListener {  }
        }
    }
}