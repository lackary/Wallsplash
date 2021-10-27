package com.lacklab.app.wallsplash.ui.view

import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.lacklab.app.wallsplash.MySuggestionProvider
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.base.BaseFragment
import com.lacklab.app.wallsplash.databinding.FragmentSearchBinding
import com.lacklab.app.wallsplash.util.TAB_COLLECTIONS
import com.lacklab.app.wallsplash.util.TAB_PHOTOS
import com.lacklab.app.wallsplash.util.TAB_USERS
import com.lacklab.app.wallsplash.ui.viewadapter.PhotoPagingAdapter
import com.lacklab.app.wallsplash.ui.viewadapter.ViewPagerAdapter
import com.lacklab.app.wallsplash.ui.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    private lateinit var searchViewModel: SearchViewModel
    private var searchJob: Job? = null
    private var viewPagerAdapter: ViewPagerAdapter? = null
    private var tabLayoutMediator: TabLayoutMediator? = null
    @Inject
    lateinit var photoPagingAdapter: PhotoPagingAdapter

    override fun layout() = R.layout.fragment_search

    override fun init() {
        initView()
        bindEvents()
    }

    override fun clear() {
        viewPagerAdapter = null
    }

    override fun clearView() {
        tabLayoutMediator?.detach()
        tabLayoutMediator = null
        with(binding!!) {
            viewPagerGallery.adapter = null
        }
        binding = null
    }

    private fun initView() {
        searchViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        with(binding!!) {
            // Adapter
//            binding.recyclerViewItems.apply {
//    //            layoutManager =
//    //                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//                adapter = photoPagingAdapter
//            }
            // SearchView
//        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
            imageSearchView.apply {
//            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
                setIconifiedByDefault(false)
                suggestionsAdapter
            }

            viewPagerAdapter = ViewPagerAdapter(childFragmentManager, lifecycle, 3)
            viewPagerGallery.apply {
                adapter = viewPagerAdapter
            }
            // connect tab layout and view pager
            tabLayoutMediator = TabLayoutMediator(tabsGallery, viewPagerGallery) { tab, position ->
                when(position) {
                    TAB_PHOTOS -> tab.text = getString(R.string.title_photos)
                    TAB_COLLECTIONS -> tab.text = getString(R.string.title_collections)
                    TAB_USERS -> tab.text = getString(R.string.title_users)
                }
            }
            tabLayoutMediator!!.attach()
        }



    }

    private fun bindEvents(){

//        binding.recyclerViewPhoto.addOnScrollListener(object: RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                Timber.d("ScrollStateChanged")
//                val layoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager
//                layoutManager.invalidateSpanAssignments()
//            }
//        })
        with(binding!!) {
            imageSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        // Hide search keyboard
                        imageSearchView.clearFocus()
                        Timber.d("queryString: $it")
                        searchViewModel.queryString.value = it
                        searchViewModel.queryString.postValue(it)
//                        searchPhotos(it)

                        // set currentData
//                    saveCurrentData(query)
//                    viewBinding.imageSearchView.post {
//                        // Important! Make sure searchView has been initialized
//                        // and referenced to the correct (current) SearchView.
//                        // Config changes (e.g. screen rotation) may make the
//                        // variable value null.
//                        viewBinding.imageSearchView.setQuery(query, false)
//                    }
                        return true
                    }?: return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }

    }

//    private fun searchPhotos(query: String) {
//        searchJob?.cancel()
//        searchJob = lifecycleScope.launch {
//            searchViewModel.searchPhotos(query).collectLatest{
//                photoPagingAdapter.submitData(it)
//            }
//        }
//    }

    private fun saveCurrentData(query: String?) {
        if (query != null) {
            val suggestions = SearchRecentSuggestions(context,
                MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE)
            suggestions.saveRecentQuery(query, null)
        }
    }
}