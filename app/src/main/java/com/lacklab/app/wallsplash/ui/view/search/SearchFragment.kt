package com.lacklab.app.wallsplash.ui.view.search

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.base.BaseFragment
import com.lacklab.app.wallsplash.databinding.FragmentSearchBinding
import com.lacklab.app.wallsplash.util.TAB_COLLECTIONS
import com.lacklab.app.wallsplash.util.TAB_PHOTOS
import com.lacklab.app.wallsplash.util.TAB_USERS
import com.lacklab.app.wallsplash.ui.adapter.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {
    private lateinit var searchViewModel: SearchViewModel
    private var searchJob: Job? = null
    private var viewPagerAdapter: ViewPagerAdapter? = null
    private var tabLayoutMediator: TabLayoutMediator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        searchViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override val layoutId: Int
        get() = R.layout.fragment_search

    override fun getVM() = searchViewModel

    override fun bindVM(binding: FragmentSearchBinding, vm: SearchViewModel) {
        with(binding) {
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

            imageSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        // Hide search keyboard
                        imageSearchView.clearFocus()
                        Timber.d("queryString: $it")
                        searchViewModel.queryString.value = it
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

//    override fun layout() = R.layout.fragment_search
//
    override fun init() {

    }

    override fun clear() {

    }

    override fun clearView() {
        tabLayoutMediator?.detach()
        tabLayoutMediator = null
        viewPagerAdapter = null
    }

}