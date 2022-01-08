package com.lacklab.app.wallsplash.ui.view.photo

import androidx.activity.viewModels
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.base.BaseActivity
import com.lacklab.app.wallsplash.databinding.ActivityPhotoBinding
import dagger.hilt.android.AndroidEntryPoint

import timber.log.Timber

@AndroidEntryPoint
class PhotoActivity : BaseActivity<ActivityPhotoBinding, PhotoViewModel>() {

    private val photoViewModel: PhotoViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.activity_photo

    override fun getVM() = photoViewModel

    override fun bindVM(binding: ActivityPhotoBinding, viewModel: PhotoViewModel) {

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
}