package com.lacklab.app.wallsplash.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.lacklab.app.wallsplash.MySuggestionProvider
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.base.BaseFragment
import com.lacklab.app.wallsplash.databinding.FragmentSearchBinding
import com.lacklab.app.wallsplash.viewadapter.ImageAdapter
import com.lacklab.app.wallsplash.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val imageAdapter = ImageAdapter()
    private val galleryViewModel: SearchViewModel by viewModels()

    private var searchJob: Job? = null

    override fun layout() = R.layout.fragment_search

    override fun init() {
        initView()
        initAction()
    }

    private fun initView() {
        // Adapter
        binding.recyclerViewPhoto.apply {
            adapter = imageAdapter
        }

        // SearchView
//        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.imageSearchView.apply {
//            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            setIconifiedByDefault(false)
            queryHint = getString(R.string.ex_search)
            suggestionsAdapter
        }
    }

    private fun initAction(){
        binding.imageSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    // Hide search keyboard
                    binding.imageSearchView.clearFocus()
                    searchPhotos(it)

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

    private fun searchPhotos(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            galleryViewModel.searchPhotos(query).collectLatest{
                imageAdapter.submitData(it)
            }
        }
    }

    private fun saveCurrentData(query: String?) {
        if (query != null) {
            val suggestions = SearchRecentSuggestions(context,
                MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE)
            suggestions.saveRecentQuery(query, null)
        }
    }
}