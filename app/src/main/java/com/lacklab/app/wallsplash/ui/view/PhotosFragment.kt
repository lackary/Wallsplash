package com.lacklab.app.wallsplash.ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.TintableBackgroundView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.base.BaseFragment
import com.lacklab.app.wallsplash.data.UnsplashPhoto
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
class PhotosFragment : BaseFragment<FragmentPhotosBinding>(),
    PhotoPagingAdapter.PhotoClickListener {

    private lateinit var searchViewModel: SearchViewModel
    private val photosViewModel: PhotosViewModel by viewModels()
    private var retrievePhotosJob: Job? = null
    private var searchPhotosJob: Job? = null
    @Inject
    lateinit var photoPagingAdapter: PhotoPagingAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
    }

    override fun clear() {
//        photoPagingAdapter = null
        retrievePhotosJob?.cancel()
        retrievePhotosJob = null
        searchPhotosJob?.cancel()
    }

    override fun clearView() {
        with(binding!!) {
            recyclerViewItems.adapter = null
        }
        binding = null
    }

    override fun layout() = R.layout.fragment_photos

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
        with(binding!!) {
            recyclerViewItems.apply {
                adapter = photoPagingAdapter
            }
        }
    }

    private fun initObserve() {
        if(parentFragment is SearchFragment) {
            searchViewModel.queryString.observe(viewLifecycleOwner, {
                searchPhotosJob?.cancel()
                searchPhotosJob = lifecycleScope.launch {
                    Timber.d("queryString: $it")
                    searchViewModel.searchPhotos(it).collectLatest {
                        photoPagingAdapter?.submitData(it)
                    }
                }
            })
        } else {
//            retrievePhotosJob?.cancel()
//            retrievePhotosJob = lifecycleScope.launch {
//                photosViewModel.getAllUnsplashPhotosLiveData().observe(viewLifecycleOwner, {
//                    photoPagingAdapter?.submitData(lifecycle, it)
//                })
//            }

            with(photosViewModel) {
                retrievePhotosJob?.cancel()
                retrievePhotosJob = lifecycleScope.launch {
                    photoFlow.collectLatest {
                        photoPagingAdapter.submitData(it)
                    }
                }
            }

        }

    }

    private fun bindEvents() {
        with(binding!!) {
            swipeRefresh.setOnRefreshListener {
                photoPagingAdapter.refresh()
            }

            with(photoPagingAdapter) {
                // bind photoClickListener event
                photoClickListener = this@PhotosFragment

                // bind load state event
                addLoadStateListener { loadStates ->
                    val isItemEmpty =
                        loadStates.refresh is LoadState.NotLoading && photoPagingAdapter.itemCount == 0
                    textViewNoResults.isVisible = isItemEmpty

                    recyclerViewItems.isVisible = loadStates.source.refresh is LoadState.NotLoading

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
        }
    }

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
        with(binding!!) {
            swipeRefresh.isRefreshing = loading == true
        }
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