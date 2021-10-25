package com.lacklab.app.wallsplash.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lacklab.app.wallsplash.databinding.ActivityPhotoBinding

import timber.log.Timber

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