package com.lacklab.app.wallsplash.ui.view.photo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lacklab.app.wallsplash.databinding.ActivityPhotoBinding
import dagger.hilt.android.AndroidEntryPoint

import timber.log.Timber

@AndroidEntryPoint
class PhotoActivity : AppCompatActivity() {

    private var viewBinding: ActivityPhotoBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val funName = object{}.javaClass.enclosingMethod.name
        Timber.d(funName)
        viewBinding = ActivityPhotoBinding.inflate(layoutInflater)
        setContentView(viewBinding!!.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}