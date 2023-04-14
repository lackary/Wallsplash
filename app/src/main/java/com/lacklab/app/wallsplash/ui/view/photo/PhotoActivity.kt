package com.lacklab.app.wallsplash.ui.view.photo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.base.BaseActivity
import com.lacklab.app.wallsplash.data.model.UnsplashPhoto
import com.lacklab.app.wallsplash.databinding.ActivityPhotoBinding
import com.lacklab.app.wallsplash.util.UiState
import com.lacklab.app.wallsplash.util.UnsplashItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

import timber.log.Timber

@AndroidEntryPoint
class PhotoActivity : BaseActivity<ActivityPhotoBinding, PhotoViewModel>() {

    private val photoViewModel: PhotoViewModel by viewModels()
    private var photoItemBundle: Bundle? = null
    private var photo: UnsplashPhoto? = null

    override val layoutId: Int
        get() = R.layout.activity_photo

    override fun getVM() = photoViewModel

    override fun bindVM(binding: ActivityPhotoBinding, viewModel: PhotoViewModel) {
        photoItemBundle = intent.getBundleExtra("photoItemBundle")!!
        photo = photoItemBundle!!.getParcelable<UnsplashPhoto>("photoItem")
        with(binding) {
            // Set the name of the view's which will be transition
            ViewCompat.setTransitionName(imageViewPhoto, photo!!.id)
            showPhoto(this, photo!!)
        }
        bindEvents(binding)
        with(viewModel) {
            photo.observe(this@PhotoActivity, Observer {
                timber.log.Timber.d("photo observe ${it.id}")
            })
            collection.observe(this@PhotoActivity, Observer {
                timber.log.Timber.d("collection observe ${it.totalPhotos}")
            })
            lifecycleScope.launch {
                this@PhotoActivity.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                    uiState.collect {
                        when(it) {
                            is UiState.Loading -> {
                                it as UiState.Loading
                                timber.log.Timber.d("LOADING ${it.data}")
                            }
                            is UiState.Success -> {
                                it as UiState.Success
                                timber.log.Timber.d("SUCCESS ${it.data}")
                            }
                            is UiState.Error -> {
                                it as UiState.Error
                                timber.log.Timber.d("Error ${it.data} and ${it.message}")
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        val funName = object{}.javaClass.enclosingMethod?.name
        Timber.d(funName)
    }

    override fun onResume() {
        super.onResume()
        val funName = object{}.javaClass.enclosingMethod?.name
        Timber.d(funName)
    }

    override fun onPause() {
        super.onPause()
        val funName = object{}.javaClass.enclosingMethod?.name
        Timber.d(funName)
    }

    override fun onStop() {
        super.onStop()
        val funName = object{}.javaClass.enclosingMethod?.name
        Timber.d(funName)
    }

    override fun onRestart() {
        super.onRestart()
        val funName = object{}.javaClass.enclosingMethod?.name
        Timber.d(funName)
    }

    override fun onDestroy() {
        super.onDestroy()
        val funName = object{}.javaClass.enclosingMethod?.name
        Timber.d(funName)
    }

    private fun bindEvents(binding: ActivityPhotoBinding) {
        with(binding) {
            // set toolbar action
            toolbarTop.setNavigationOnClickListener {
                onBackPressed();
            }

            // set info action
            imageViewInfo.setOnClickListener {  }
        }
    }

    private fun showPhoto(binding: ActivityPhotoBinding, photo: UnsplashPhoto) {
        with(binding) {
            photoItem = photo
            Glide.with(root)
                .load(photo.urls?.regular)
                .override(Target.SIZE_ORIGINAL)
                .into(imageViewPhoto)
        }
    }
}