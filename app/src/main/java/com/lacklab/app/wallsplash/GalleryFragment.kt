package com.lacklab.app.wallsplash

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.lacklab.app.wallsplash.databinding.FragmentGalleryBinding
import com.lacklab.app.wallsplash.viewadapter.GalleryAdapter
import com.lacklab.app.wallsplash.viewmodels.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private val TAG = GalleryFragment::class.java.simpleName

    private val galleryAdapter = GalleryAdapter()
    private val galleryViewModel: GalleryViewModel by viewModels()
    private lateinit var viewBinding: FragmentGalleryBinding

    private var searchJob: Job? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.v(TAG, "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.v(TAG, "onCreateView")
        viewBinding = FragmentGalleryBinding.inflate(inflater, container, false)
        viewBinding.recyclerViewPhoto.adapter = galleryAdapter


        return viewBinding.root
//        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.v(TAG, "onViewCreated")
        getPhotos()
//        testRunBlocking()
        Log.i(TAG, "done")
    }

    override fun onStart() {
        super.onStart()
        Log.v(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.v(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.v(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG, "onStop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.v(TAG, "onSaveInstanceState")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.v(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.v(TAG, "onDetach")
    }

    private fun getPhotos() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            galleryViewModel.searchPhotos("Japan").collectLatest{
                galleryAdapter.submitData(it)
            }
        }
    }

    private fun testRunBlocking() = runBlocking {
        Log.i(TAG, "testRunBlocking")
        Log.i(TAG, "current job ${coroutineContext[Job]}")
        val job = launch {
            Log.i(TAG, "Hello")
        }

        Log.i(TAG, "testRunBlocking completed")
    }
}