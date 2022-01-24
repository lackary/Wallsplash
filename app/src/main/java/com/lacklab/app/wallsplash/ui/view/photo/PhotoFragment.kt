package com.lacklab.app.wallsplash.ui.view.photo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.base.BaseFragment
import com.lacklab.app.wallsplash.data.model.EventBusTestData
import com.lacklab.app.wallsplash.data.model.UnsplashPhoto
import com.lacklab.app.wallsplash.databinding.FragmentPhotoBinding
import com.lacklab.app.wallsplash.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber

@AndroidEntryPoint
class PhotoFragment : BaseFragment<FragmentPhotoBinding, PhotoViewModel>() {
//    private val args: PhotoFragmentArgs by navArgs()
    private val photoViewModel: PhotoViewModel by viewModels()
    private var photoItemBundle: Bundle? = null
    private var photo: UnsplashPhoto? = null

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
        bindEvents(binding)
        with(vm) {
            photo.observe(viewLifecycleOwner, Observer {
                Timber.d("photo observe ${it.id}")
            })
            collection.observe(viewLifecycleOwner, Observer {
                Timber.d("collection observe ${it.totalPhotos}")
            })
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    uiState.collect {
                        when(it) {
                            is UiState.Loading -> Timber.d("LOADING")
                            is UiState.Success -> Timber.d("SUCCESS")
                            is UiState.Error -> Timber.d("Error")
                        }
                    }
                }
            }
        }
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

//    private fun initView() {
//        photoItemBundle = requireActivity().intent.getBundleExtra("photoItemBundle")!!
//        photo = photoItemBundle!!.getParcelable<UnsplashPhoto>("photoItem")
//        with(binding) {
//            photoItem = photo
//            Glide.with(root)
//                .load(photo!!.urls!!.regular)
//                .override(SIZE_ORIGINAL)
////            .addListener(object: RequestListener<Drawable>{
////                override fun onLoadFailed(
////                    e: GlideException?,
////                    model: Any?,
////                    target: Target<Drawable>?,
////                    isFirstResource: Boolean
////                ): Boolean {
////                    startPostponedEnterTransition()
////                    return false
////                }
////
////                override fun onResourceReady(
////                    resource: Drawable?,
////                    model: Any?,
////                    target: Target<Drawable>?,
////                    dataSource: DataSource?,
////                    isFirstResource: Boolean
////                ): Boolean {
////                    startPostponedEnterTransition()
////                    return false
////                }
////
////            })
//                .into(imageViewPhoto)
//        }
//
//    }

    private fun bindEvents(binding: FragmentPhotoBinding) {
        with(binding) {
            // set toolbar action
            toolbarTop.setNavigationOnClickListener {
                requireActivity().onBackPressed();
            }

            // set info action
            imageViewInfo.setOnClickListener {  }
        }
    }

    override fun init() {
        EventBus.getDefault().register(this)
    }

    override fun clear() {
        EventBus.getDefault().unregister(this)
    }

    override fun clearView() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater
                .from(context!!)
                .inflateTransition(android.R.transition.move)
    }

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onStop() {
        super.onStop()
        EventBus.clearCaches()
        EventBus.getDefault().unregister(this)

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessage(eventBusTestData: EventBusTestData) {
        Timber.d("message")
        val temp = 1
    }
}