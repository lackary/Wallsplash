package com.lacklab.app.wallsplash.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.base.BaseFragment
import com.lacklab.app.wallsplash.databinding.FragmentGalleryBinding
import com.lacklab.app.wallsplash.viewadapter.ImageAdapter
import com.lacklab.app.wallsplash.viewmodels.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class GalleryFragment : BaseFragment<FragmentGalleryBinding>() {

//    private lateinit var viewBinding: FragmentGalleryBinding

    private val imageAdapter = ImageAdapter()
    private val galleryViewModel: GalleryViewModel by viewModels()

    private var retrievePhotoJob: Job? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        getPhotos()
//        testRunBlocking()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun init() {
        initView()
        initObserve()
        initPageDataAdapter()
    }

    override fun layout() = R.layout.fragment_gallery

    private fun initView() {
        binding.recyclerViewPhoto.adapter = imageAdapter
    }

    private fun initObserve() {
        retrievePhotoJob = lifecycleScope.launch {
            galleryViewModel.getAllUnsplashPhotosLiveData().observe(viewLifecycleOwner, {
                imageAdapter.submitData(lifecycle, it)
            })
        }
    }

    private fun initPageDataAdapter() {
//        imageAdapter.addLoadStateListener {
//
//        }
    }

    private fun getPhotos() {
        retrievePhotoJob?.cancel()
        // Flow
        retrievePhotoJob = lifecycleScope.launch {
            galleryViewModel.getAllUnsplashPhotos().collectLatest {
                imageAdapter.submitData(it)
            }
        }
    }

    private fun testRunBlocking() = runBlocking {

    }
}