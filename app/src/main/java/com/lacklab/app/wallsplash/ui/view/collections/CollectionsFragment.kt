package com.lacklab.app.wallsplash.ui.view.collections

import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.base.BaseFragment
import com.lacklab.app.wallsplash.util.UnsplashItem
import com.lacklab.app.wallsplash.databinding.FragmentCollectionsBinding
import com.lacklab.app.wallsplash.ui.view.search.SearchFragment
import com.lacklab.app.wallsplash.ui.adapter.PagingLoadStateAdapter
import com.lacklab.app.wallsplash.ui.adapter.UnsplashPagingAdapter
import com.lacklab.app.wallsplash.ui.view.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class CollectionsFragment : BaseFragment<FragmentCollectionsBinding, CollectionsViewModel>(),
    UnsplashPagingAdapter.ItemClickListener{

    private lateinit var searchViewModel: SearchViewModel
    private val collectionsViewModel: CollectionsViewModel by viewModels()
    @Inject
    @Nullable
    lateinit var collectionPagingAdapter: UnsplashPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
    }
    override val layoutId: Int
        get() = R.layout.fragment_collections

    override fun getVM() = collectionsViewModel

    override fun bindVM(binding: FragmentCollectionsBinding, vm: CollectionsViewModel) {
        with(binding) {
            with(collectionPagingAdapter) {
                itemClickListener = this@CollectionsFragment
                recyclerViewItems.adapter = withLoadStateFooter(
                    footer = PagingLoadStateAdapter(this)
                )
                swipeRefresh.setOnRefreshListener { refresh() }
                with(vm) {
                    if(parentFragment is SearchFragment) {
                        searchViewModel.queryString.observe(viewLifecycleOwner, {
                            launchOnLifecycleScope{
                                Timber.d("queryString: $it")
                                searchViewModel.searchCollections(it).collectLatest {
                                    submitData(it)
                                }
                            }
                        })
                    } else {
                        launchOnLifecycleScope {
                            itemsFlow.collectLatest { submitData(it) }
                        }
                    }
                    launchOnLifecycleScope {
                        loadStateFlow.collectLatest {
                            swipeRefresh.isRefreshing = it.refresh is LoadState.Loading

                            val isItemEmpty =
                                it.refresh !is LoadState.Loading
                                        && collectionPagingAdapter.itemCount == 0
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

    override fun onPhotoClicked(item: UnsplashItem, view: View) {
        Timber.d("photo clicked")
    }

    override fun onUserClicked(item: UnsplashItem, view: View) {
        Timber.d("user clicked")
    }
}