package com.lacklab.app.wallsplash.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
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
import timber.log.Timber

@AndroidEntryPoint
class GalleryFragment : BaseFragment<FragmentGalleryBinding>() {

    private val imageAdapter by lazy {
        ImageAdapter(
            photoClickListener = { photoItem, view ->
                val direction =
                    GalleryFragmentDirections.actionNavigationPhotoLibraryToNavigationPhoto(photoItem)
                val extras = FragmentNavigatorExtras(view to photoItem.id )
                view.findNavController()
                    .navigate(direction, extras)
            },
            nameClickListener = { photoItem, view ->
                Timber.d("click name")
            }
        )
    }



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
        initAction()
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

    private fun initAction() {
        // init bottom appbar action
        binding.bottomAppbar.setNavigationOnClickListener {
            // GO TO About
        }
        binding.bottomAppbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.item_search -> {
                    true
                }
                else -> false
            }
        }

    }

    private fun initPageDataAdapter() {
        // init image adapter action
        imageAdapter.addLoadStateListener {

        }
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