package com.lacklab.app.wallsplash.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.base.BaseFragment
import com.lacklab.app.wallsplash.databinding.FragmentGalleryBinding
import com.lacklab.app.wallsplash.util.TAB_COLLECTIONS
import com.lacklab.app.wallsplash.util.TAB_PHOTOS
import com.lacklab.app.wallsplash.viewadapter.PhotoAdapter
import com.lacklab.app.wallsplash.viewadapter.ViewPagerAdapter
import com.lacklab.app.wallsplash.viewmodels.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class GalleryFragment : BaseFragment<FragmentGalleryBinding>() {

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun layout() = R.layout.fragment_gallery

    override fun init() {
        initView()
    }

    private fun initView() {
        // init view pager
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager, lifecycle, 2)
        binding.viewPagerGallery.apply {
            adapter = viewPagerAdapter
        }
        // connect tab layout and view pager
        TabLayoutMediator(binding.tabsGallery, binding.viewPagerGallery) { tab, position ->
            when(position) {
                TAB_PHOTOS -> tab.text = getString(R.string.title_photos)
                TAB_COLLECTIONS -> tab.text = getString(R.string.title_collections)
            }
        }.attach()
    }

    private fun initAction() {

    }

}