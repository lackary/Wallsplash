package com.lacklab.app.wallsplash.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.base.BaseFragment
import com.lacklab.app.wallsplash.data.model.UnsplashPhoto
import com.lacklab.app.wallsplash.databinding.FragmentPhotoBinding
import com.lacklab.app.wallsplash.ui.viewmodels.PhotoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoFragment : BaseFragment<FragmentPhotoBinding, PhotoViewModel>() {
//    private val args: PhotoFragmentArgs by navArgs()
    private val photoViewModel: PhotoViewModel by viewModels()
    private var photoItemBundle: Bundle? = null
    private var photo: UnsplashPhoto? = null
    private lateinit var binding:FragmentPhotoBinding

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo, container, false)
//        return binding.root
//    }

    override val layoutId: Int
        get() = R.layout.fragment_photo

    override fun getVM() = photoViewModel

    override fun bindVM(binding: FragmentPhotoBinding, vm: PhotoViewModel) {
        photoItemBundle = requireActivity().intent.getBundleExtra("photoItemBundle")!!
        photo = photoItemBundle!!.getParcelable<UnsplashPhoto>("photoItem")
        with(binding) {
            photoItem = photo
            Glide.with(root)
                .load(photo!!.urls!!.regular)
                .override(SIZE_ORIGINAL)
                .into(imageViewPhoto)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater
                .from(context)
                .inflateTransition(android.R.transition.move)

//        initView()
//        bindEvents()
    }

//    override fun init() {
//        initView()
//        bindEvents()
//    }

//    override fun clear() {
//        photoItemBundle = null
//        photo = null
//    }
//
//    override fun clearView() {
////        binding = null
//    }

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

    override fun clear() {

    }

    override fun clearView() {

    }
}