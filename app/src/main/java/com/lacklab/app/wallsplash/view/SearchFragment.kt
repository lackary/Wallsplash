package com.lacklab.app.wallsplash.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.lacklab.app.wallsplash.MySuggestionProvider
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.databinding.FragmentSearchBinding
import com.lacklab.app.wallsplash.viewadapter.ImageAdapter
import com.lacklab.app.wallsplash.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var viewBinding: FragmentSearchBinding

    private val imageAdapter = ImageAdapter()
    private val galleryViewModel: SearchViewModel by viewModels()

    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val funName = object{}.javaClass.enclosingMethod.name
        Timber.d(funName)
        viewBinding = FragmentSearchBinding.inflate(inflater, container, false)
        viewBinding.recyclerViewPhoto.adapter = imageAdapter

        // SearchView
//        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        viewBinding.imageSearchView.apply {
//            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            setIconifiedByDefault(false)
            queryHint = getString(R.string.ex_search)
            suggestionsAdapter
        }

        viewBinding.imageSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    // Hide search keyboard
                    viewBinding.imageSearchView.clearFocus()
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
        return viewBinding.root
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