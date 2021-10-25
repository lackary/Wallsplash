package com.lacklab.app.wallsplash.view

import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
import androidx.recyclerview.widget.StaggeredGridLayoutManager.GAP_HANDLING_NONE
import com.google.android.material.tabs.TabLayoutMediator
import com.lacklab.app.wallsplash.MySuggestionProvider
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.base.BaseFragment
import com.lacklab.app.wallsplash.databinding.FragmentSearchBinding
import com.lacklab.app.wallsplash.util.TAB_COLLECTIONS
import com.lacklab.app.wallsplash.util.TAB_PHOTOS
import com.lacklab.app.wallsplash.util.TAB_USERS
import com.lacklab.app.wallsplash.viewadapter.PhotoPagingAdapter
import com.lacklab.app.wallsplash.viewadapter.ViewPagerAdapter
import com.lacklab.app.wallsplash.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    private val photoPagingAdapter by lazy {
        PhotoPagingAdapter(
            photoClickListener = { photoItem, view ->
//                val direction =
//                    SearchFragmentDirections.actionNavigationImageSearchToNavigationPhoto(photoItem)
//                val extras = FragmentNavigatorExtras(view to photoItem.id)
//                view.findNavController()
//                    .navigate(direction, extras)
                val intent = Intent(requireActivity(), PhotoActivity::class.java)
                val bundle = Bundle().apply {
                    putParcelable("photoItem", photoItem)
                }
                intent.putExtra("photoItemBundle", bundle)
                val activityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this@SearchFragment.requireActivity(),
                        view,
                        photoItem.id
                    )
                startActivity(intent, activityOptionsCompat.toBundle())
            },
            nameClickListener = { photoItem, view ->
                Timber.d("click name")
            }
        )
    }

    private val galleryViewModel: SearchViewModel by viewModels()

    private var searchJob: Job? = null

    override fun layout() = R.layout.fragment_search

    override fun init() {
        initView()
        bindEvents()
    }

    private fun initView() {
        with(binding) {
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
            TabLayoutMediator(tabsGallery, viewPagerGallery) { tab, position ->
                when(position) {
                    TAB_PHOTOS -> tab.text = getString(R.string.title_photos)
                    TAB_COLLECTIONS -> tab.text = getString(R.string.title_collections)
                    TAB_USERS -> tab.text = getString(R.string.title_users)
                }
            }.attach()
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
                photoPagingAdapter.submitData(it)
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