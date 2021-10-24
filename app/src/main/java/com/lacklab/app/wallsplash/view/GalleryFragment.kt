package com.lacklab.app.wallsplash.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
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
//                val direction =
//                    GalleryFragmentDirections.actionNavigationPhotoLibraryToNavigationPhoto(photoItem)
//                // use shareElement
//                val extras = FragmentNavigatorExtras(view to photoItem.id )
//                view.findNavController()
//                    .navigate(direction, extras)
                val intent = Intent(requireActivity(), PhotoActivity::class.java)
                val bundle = Bundle().apply {
                    putParcelable("photoItem", photoItem)
                }
                intent.putExtra("photoItemBundle", bundle)
                val activityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this@GalleryFragment.requireActivity(),
                        view,
                        photoItem.id
                    )
                startActivity(intent, activityOptionsCompat.toBundle())
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
        bindEvents()
        initPageDataAdapter()
    }

    override fun layout() = R.layout.fragment_gallery

    private fun initView() {
        binding.recyclerViewItems.apply {
            adapter = imageAdapter
        }
    }

    private fun initObserve() {
        retrievePhotoJob = lifecycleScope.launch {
            galleryViewModel.getAllUnsplashPhotosLiveData().observe(viewLifecycleOwner, {
                imageAdapter.submitData(lifecycle, it)
            })
        }
    }

    private fun bindEvents() {
//        // init bottom appbar action
//        binding.bottomAppbar.setNavigationOnClickListener {
//            // GO TO About
//        }
//        binding.bottomAppbar.setOnMenuItemClickListener {
//            when(it.itemId) {
//                R.id.item_search -> {
////                    val direction =
////                        GalleryFragmentDirections
////                            .actionNavigationPhotoLibraryToNavigationImageSearch()
////                    findNavController().navigate(direction)
//                    val intent = Intent(activity, SearchActivity::class.java)
//                    startActivity(intent);
//                    true
//                }
//                else -> false
//            }
//        }
        with(binding) {
            swipeRefresh.setOnRefreshListener {
                imageAdapter.refresh()
            }
        }

    }

    private fun initPageDataAdapter() {
        // init image adapter action
        imageAdapter.addLoadStateListener { loadStates ->
            val isItemEmpty =
                loadStates.refresh is LoadState.NotLoading && imageAdapter.itemCount == 0
            binding.textViewNoResults.isVisible = isItemEmpty

            binding.recyclerViewItems.isVisible = loadStates.source.refresh is LoadState.NotLoading

            // show the swiper during init
            handleLoading(loadStates.source.refresh is LoadState.Loading)

            val test = loadStates.source.refresh is LoadState.Error

            // If we have an error, show a toast
            val errorState = when {
                loadStates.append is LoadState.Error -> loadStates.append as LoadState.Error
                loadStates.prepend is LoadState.Error -> loadStates.prepend as LoadState.Error
                loadStates.refresh is LoadState.Error -> loadStates.refresh as LoadState.Error
                else -> null
            }
            errorState?.let {
                showToastMessage(it.error.message.toString())
            }
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

    private fun handleLoading(loading: Boolean) {
        with(binding) {
            swipeRefresh.isRefreshing = loading == true
        }
    }
}