package com.lacklab.app.wallsplash.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lacklab.app.wallsplash.databinding.ActivitySearchBinding
import timber.log.Timber

class SearchActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val funName = object{}.javaClass.enclosingMethod.name
        Timber.d("")

    }
}