package com.lacklab.app.wallsplash.ui.view.photos

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.base.BaseFragment
import com.lacklab.app.wallsplash.util.UnsplashItem
import com.lacklab.app.wallsplash.databinding.FragmentPhotosBinding
import com.lacklab.app.wallsplash.ui.adapter.PagingLoadStateAdapter
import com.lacklab.app.wallsplash.ui.view.photo.PhotoActivity
import com.lacklab.app.wallsplash.ui.view.search.SearchFragment
import com.lacklab.app.wallsplash.ui.adapter.UnsplashPagingAdapter
import com.lacklab.app.wallsplash.ui.view.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class PhotosFragment : BaseFragment<FragmentPhotosBinding, PhotosViewModel>(),
    UnsplashPagingAdapter.ItemClickListener {

    private lateinit var searchViewModel: SearchViewModel
    private val photosViewModel: PhotosViewModel by activityViewModels()
    @Inject
    lateinit var photoPagingAdapter: UnsplashPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        searchViewModel = ViewModelProvider(requireActivity())[SearchViewModel::class.java]
        super.onCreate(savedInstanceState)
    }

    override val layoutId: Int
        get() = R.layout.fragment_photos

    override fun getVM() = photosViewModel

    override fun bindVM(binding: FragmentPhotosBinding, vm: PhotosViewModel) {
        with(binding) {
            with(photoPagingAdapter) {
                itemClickListener = this@PhotosFragment
                recyclerViewItems.adapter = withLoadStateFooter(
                    footer = PagingLoadStateAdapter(this)
                )
                swipeRefresh.setOnRefreshListener { refresh() }
                with(vm) {
                    if(parentFragment is SearchFragment) {
                        searchViewModel.queryString.observe(viewLifecycleOwner) {
                            launchOnLifecycleScope {
                                Timber.d("queryString: $it")
                                searchViewModel.searchPhotos(it).collectLatest {
                                    submitData(it)
                                }
                            }
                        }
                    } else {
                        launchOnLifecycleScope {
                            itemsFlow.collectLatest { submitData(it) }
                        }
                    }

                    launchOnLifecycleScope {
                        loadStateFlow.collectLatest { it ->
                            swipeRefresh.isRefreshing = it.refresh is LoadState.Loading

                            val isItemEmpty =
                                it.refresh !is LoadState.Loading
                                        && photoPagingAdapter.itemCount == 0
                            textViewNoResults.isVisible = isItemEmpty
                            recyclerViewItems.isVisible = !isItemEmpty

                            // If we have an error, show a toast
                            val errorState = when {
                                it.append is LoadState.Error -> it.append as LoadState.Error
                                it.prepend is LoadState.Error -> it.prepend as LoadState.Error
                                it.refresh is LoadState.Error -> it.refresh as LoadState.Error
                                else -> null
                            }
                            errorState?.let {
                                showToastMessage(it.error.message.toString())
                            }
                        }
                    }
                }
            }
        }
    }

    override fun init() {

    }

    override fun clear() {

    }

    override fun clearView() {

    }

//    override fun layout() = R.layout.fragment_photos
    override fun onPhotoClicked(item: UnsplashItem, view: View) {
        val intent = Intent(requireActivity(), PhotoActivity::class.java)
        val bundle = Bundle().apply {
            putParcelable("photoItem", (item as UnsplashItem.Photo).data)
        }
        intent.putExtra("photoItemBundle", bundle)
        val activityOptionsCompat =
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),
                view,
                (item as UnsplashItem.Photo).data.id
            )
        startActivity(intent, activityOptionsCompat.toBundle())
    }

    override fun onUserClicked(item: UnsplashItem, view: View) {
        Timber.d("click user")
    }
}