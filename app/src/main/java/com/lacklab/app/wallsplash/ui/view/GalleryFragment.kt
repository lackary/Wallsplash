package com.lacklab.app.wallsplash.ui.view

import com.google.android.material.tabs.TabLayoutMediator
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.base.BaseFragment
import com.lacklab.app.wallsplash.databinding.FragmentGalleryBinding
import com.lacklab.app.wallsplash.ui.viewadapter.ViewPagerAdapter
import com.lacklab.app.wallsplash.util.TAB_COLLECTIONS
import com.lacklab.app.wallsplash.util.TAB_PHOTOS
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : BaseFragment<FragmentGalleryBinding>() {

    private var viewPagerAdapter: ViewPagerAdapter? = null
    private var tabLayoutMediator: TabLayoutMediator? = null

    override fun layout() = R.layout.fragment_gallery

    override fun init() {
        initView()
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
        with(binding!!) {
            // init view pager
            viewPagerAdapter = ViewPagerAdapter(childFragmentManager, lifecycle, 2)
            viewPagerGallery.apply {
                adapter = viewPagerAdapter
            }
            // connect tab layout and view pager
            tabLayoutMediator = TabLayoutMediator(tabsGallery, viewPagerGallery) { tab, position ->
                when(position) {
                    TAB_PHOTOS -> tab.text = getString(R.string.title_photos)
                    TAB_COLLECTIONS -> tab.text = getString(R.string.title_collections)
                }
            }
            tabLayoutMediator!!.attach()
        }

    }

    private fun bindEvents() {

    }

}