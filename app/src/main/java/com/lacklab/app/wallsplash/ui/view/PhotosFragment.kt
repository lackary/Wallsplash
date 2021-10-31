package com.lacklab.app.wallsplash.ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.base.BaseFragment
import com.lacklab.app.wallsplash.data.model.UnsplashPhoto
import com.lacklab.app.wallsplash.databinding.FragmentPhotosBinding
import com.lacklab.app.wallsplash.ui.viewadapter.PhotoPagingAdapter
import com.lacklab.app.wallsplash.ui.viewmodels.PhotosViewModel
import com.lacklab.app.wallsplash.ui.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class PhotosFragment : BaseFragment<FragmentPhotosBinding, PhotosViewModel>(),
    PhotoPagingAdapter.PhotoClickListener {

    private lateinit var searchViewModel: SearchViewModel
    private val photosViewModel: PhotosViewModel by viewModels()
    private var retrievePhotosJob: Job? = null
    private var searchPhotosJob: Job? = null
    @Inject
    lateinit var photoPagingAdapter: PhotoPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        searchViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override val layoutId: Int
        get() = R.layout.fragment_photos

    override fun getVM() = photosViewModel

    override fun bindVM(binding: FragmentPhotosBinding, vm: PhotosViewModel) {
        with(binding) {
            with(photoPagingAdapter) {
                photoClickListener = this@PhotosFragment
                recyclerViewItems.adapter = this
                swipeRefresh.setOnRefreshListener { refresh() }
                with(vm) {
                    if(parentFragment is SearchFragment) {
                        searchViewModel.queryString.observe(viewLifecycleOwner, {
                            searchPhotosJob?.cancel()
                            searchPhotosJob = lifecycleScope.launch {
                                Timber.d("queryString: $it")
                                searchViewModel.searchPhotos(it).collectLatest {
                                    submitData(it)
                                }
                            }
                        })
                    } else {
                        launchOnLifecycleScope {
                            photoFlow.collectLatest { submitData(it) }
                        }
                    }

                    launchOnLifecycleScope {
                        loadStateFlow.collectLatest {
                            swipeRefresh.isRefreshing = it.refresh is LoadState.Loading
                        }
                    }
                }
            }
        }
    }

//    override fun init() {
//        initView()
//        initObserve()
//        bindEvents()
//    }

    override fun clear() {
//        photoPagingAdapter = null
        retrievePhotosJob?.cancel()
        retrievePhotosJob = null
        searchPhotosJob?.cancel()
    }

    override fun clearView() {
//        with(binding!!) {
//            recyclerViewItems.adapter = null
//        }
//        binding = null
    }

//    override fun layout() = R.layout.fragment_photos

    private fun initView() {
        searchViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
//        photoPagingAdapter = PhotoPagingAdapter(
//            photoClickListener = { photoItem, view ->
////                val direction =
////                    GalleryFragmentDirections.actionNavigationPhotoLibraryToNavigationPhoto(photoItem)
////                // use shareElement
////                val extras = FragmentNavigatorExtras(view to photoItem.id )
////                view.findNavController()
////                    .navigate(direction, extras)
//                val intent = Intent(requireActivity(), PhotoActivity::class.java)
//                val bundle = Bundle().apply {
//                    putParcelable("photoItem", photoItem)
//                }
//                intent.putExtra("photoItemBundle", bundle)
//                val activityOptionsCompat =
//                    ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        requireActivity(),
//                        view,
//                        photoItem.id
//                    )
//                startActivity(intent, activityOptionsCompat.toBundle())
//            },
//            nameClickListener = { photoItem, view ->
//                Timber.d("click name")
//            }
//        )
//        with(binding!!) {
//            recyclerViewItems.apply {
//                adapter = photoPagingAdapter
//            }
//        }
    }

//    private fun initObserve() {
//        if(parentFragment is SearchFragment) {
//            searchViewModel.queryString.observe(viewLifecycleOwner, {
//                searchPhotosJob?.cancel()
//                searchPhotosJob = lifecycleScope.launch {
//                    Timber.d("queryString: $it")
//                    searchViewModel.searchPhotos(it).collectLatest {
//                        photoPagingAdapter?.submitData(it)
//                    }
//                }
//            })
//        } else {
//            with(photosViewModel) {
//                retrievePhotosJob?.cancel()
//                retrievePhotosJob = lifecycleScope.launch {
//                    photoFlow.collectLatest {
//                        photoPagingAdapter.submitData(it)
//                    }
//                }
//            }
//
//        }
//
//    }

//    private fun bindEvents() {
//        with(binding!!) {
//            swipeRefresh.setOnRefreshListener {
//                photoPagingAdapter.refresh()
//            }
//
//            with(photoPagingAdapter) {
//                // bind photoClickListener event
//                photoClickListener = this@PhotosFragment
//
//                // bind load state event
//                addLoadStateListener { loadStates ->
//                    val isItemEmpty =
//                        loadStates.refresh is LoadState.NotLoading && photoPagingAdapter.itemCount == 0
//                    textViewNoResults.isVisible = isItemEmpty
//
//                    recyclerViewItems.isVisible = loadStates.source.refresh is LoadState.NotLoading
//
//                    // show the swiper during init
//                    handleLoading(loadStates.source.refresh is LoadState.Loading)
//
//                    val test = loadStates.source.refresh is LoadState.Error
//
//                    // If we have an error, show a toast
//                    val errorState = when {
//                        loadStates.append is LoadState.Error -> loadStates.append as LoadState.Error
//                        loadStates.prepend is LoadState.Error -> loadStates.prepend as LoadState.Error
//                        loadStates.refresh is LoadState.Error -> loadStates.refresh as LoadState.Error
//                        else -> null
//                    }
//                    errorState?.let {
//                        showToastMessage(it.error.message.toString())
//                    }
//                }
//            }
//        }
//    }

//    private fun getPhotos() {
//        retrievePhotosJob?.cancel()
//        // Flow
//        retrievePhotosJob = lifecycleScope.launch {
//            photosViewModel.getAllUnsplashPhotos().collectLatest {
//                photoPagingAdapter.submitData(it)
//            }
//        }
//    }

    private fun testRunBlocking() = runBlocking {

    }

    private fun handleLoading(loading: Boolean) {
//        with(binding!!) {
//            swipeRefresh.isRefreshing = loading == true
//        }
    }

    override fun onPhotoClicked(item: UnsplashPhoto, view: View) {
        val intent = Intent(requireActivity(), PhotoActivity::class.java)
        val bundle = Bundle().apply {
            putParcelable("photoItem", item)
        }
        intent.putExtra("photoItemBundle", bundle)
        val activityOptionsCompat =
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),
                view,
                item.id
            )
        startActivity(intent, activityOptionsCompat.toBundle())
    }

    override fun onUserClicked(item: UnsplashPhoto, view: View) {
        Timber.d("click user")
    }
}