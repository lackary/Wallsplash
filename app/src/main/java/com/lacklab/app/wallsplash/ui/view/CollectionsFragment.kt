package com.lacklab.app.wallsplash.ui.view

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.base.BaseFragment
import com.lacklab.app.wallsplash.databinding.FragmentCollectionsBinding
import com.lacklab.app.wallsplash.ui.viewadapter.CollectionPagingAdapter
import com.lacklab.app.wallsplash.ui.viewmodels.CollectionsViewModel
import com.lacklab.app.wallsplash.ui.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CollectionsFragment : BaseFragment<FragmentCollectionsBinding>() {

    private lateinit var searchViewModel: SearchViewModel
    private val collectionsViewModel: CollectionsViewModel by viewModels()
    private var retrieveCollectionsJob: Job? = null
    private var searchCollectionJob: Job? = null
    private var collectionPagingAdapter: CollectionPagingAdapter? = null

    override fun layout(): Int = R.layout.fragment_collections

    override fun onDestroyView() {
        super.onDestroyView()
        collectionPagingAdapter = null
    }

    override fun init() {
        initView()
        initObserve()
        bindEvents()
    }

    override fun clear() {
        collectionPagingAdapter = null
        retrieveCollectionsJob?.cancel()
        retrieveCollectionsJob = null
    }

    override fun clearView() {
        with(binding!!) {
            recyclerViewItems.adapter = null
        }
        binding = null
    }

    private fun initView() {
        searchViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        collectionPagingAdapter = CollectionPagingAdapter(
            photoClickListener = { photoItem, view ->
                Timber.d("click photo")
            },
            nameClickListener = { photoItem, view ->
                Timber.d("click name")
            }
        )
        with(binding!!){
            recyclerViewItems.apply{
                adapter = collectionPagingAdapter
            }
        }
    }

    private fun initObserve() {
        if(parentFragment is SearchFragment) {
            searchViewModel.queryString.observe(viewLifecycleOwner, {
                searchCollectionJob?.cancel()
                searchCollectionJob = lifecycleScope.launch {

                }
            })
        } else {
            retrieveCollectionsJob?.cancel()
            retrieveCollectionsJob = lifecycleScope.launch {
                collectionsViewModel.getCollectionsLiveData().observe(viewLifecycleOwner, {
                    collectionPagingAdapter?.submitData(lifecycle, it)
                })
            }
        }
    }

    private fun bindEvents() {
        with(binding!!) {
            swipeRefresh.setOnRefreshListener {
                collectionPagingAdapter?.refresh()
            }

            collectionPagingAdapter?.addLoadStateListener { loadStates ->
                val isItemEmpty =
                    loadStates.refresh is LoadState.NotLoading
                            && collectionPagingAdapter?.itemCount == 0
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

    private fun handleLoading(loading: Boolean) {
        with(binding!!) {
            swipeRefresh.isRefreshing = loading == true
        }
    }
}