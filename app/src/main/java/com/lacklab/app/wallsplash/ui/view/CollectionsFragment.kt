package com.lacklab.app.wallsplash.ui.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.base.BaseFragment
import com.lacklab.app.wallsplash.data.model.UnsplashCollection
import com.lacklab.app.wallsplash.databinding.FragmentCollectionsBinding
import com.lacklab.app.wallsplash.ui.viewadapter.CollectionPagingAdapter
import com.lacklab.app.wallsplash.ui.viewadapter.PagingLoadStateAdapter
import com.lacklab.app.wallsplash.ui.viewmodels.CollectionsViewModel
import com.lacklab.app.wallsplash.ui.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class CollectionsFragment : BaseFragment<FragmentCollectionsBinding, CollectionsViewModel>(),
CollectionPagingAdapter.CollectionClickListener{

    private lateinit var searchViewModel: SearchViewModel
    private val collectionsViewModel: CollectionsViewModel by viewModels()
    private var searchCollectionsJob: Job? = null
    @Inject
    lateinit var collectionPagingAdapter: CollectionPagingAdapter

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
                collectionClickListener = this@CollectionsFragment
                recyclerViewItems.adapter = withLoadStateFooter(
                    footer = PagingLoadStateAdapter(this)
                )
                swipeRefresh.setOnRefreshListener { refresh() }
                with(vm) {
                    if(parentFragment is SearchFragment) {
                        searchViewModel.queryString.observe(viewLifecycleOwner, {
                            searchCollectionsJob?.cancel()
                            searchCollectionsJob = lifecycleScope.launch {
                                Timber.d("queryString: $it")
                                searchViewModel.searchCollections(it).collectLatest {
                                    submitData(it)
                                }
                            }
                        })
                    } else {
                        launchOnLifecycleScope {
                            collectionFlow.collectLatest { submitData(it) }
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

    override fun clear() {
        searchCollectionsJob?.cancel()
        searchCollectionsJob = null
    }

    override fun clearView() {

    }

    override fun onPhotoClicked(item: UnsplashCollection, view: View) {
        Timber.d("photo clicked")
    }

    override fun onUserClicked(item: UnsplashCollection, view: View) {
        Timber.d("user clicked")
    }
}