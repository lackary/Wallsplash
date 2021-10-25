package com.lacklab.app.wallsplash.view

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.base.BaseFragment
import com.lacklab.app.wallsplash.databinding.FragmentCollectionsBinding
import com.lacklab.app.wallsplash.viewadapter.CollectionPagingAdapter
import com.lacklab.app.wallsplash.viewmodels.CollectionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CollectionsFragment : BaseFragment<FragmentCollectionsBinding>() {

    private val collectionsViewModel: CollectionsViewModel by viewModels()
    private var retrieveCollectionsJob: Job? = null
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

    private fun initView() {
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

    override fun clear() {
        with(binding!!) {
            recyclerViewItems.adapter = null
        }
        binding = null
        collectionPagingAdapter = null
        retrieveCollectionsJob?.cancel()
        retrieveCollectionsJob = null
    }

    private fun initObserve() {
        retrieveCollectionsJob?.cancel()
        if(parentFragment is SearchFragment) return
        retrieveCollectionsJob = lifecycleScope.launch {
            collectionsViewModel.getCollectionsLiveData().observe(viewLifecycleOwner, {
                collectionPagingAdapter?.submitData(lifecycle, it)
            })
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