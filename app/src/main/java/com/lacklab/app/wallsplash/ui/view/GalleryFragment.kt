package com.lacklab.app.wallsplash.ui.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.base.BaseFragment
import com.lacklab.app.wallsplash.databinding.FragmentGalleryBinding
import com.lacklab.app.wallsplash.ui.viewadapter.ViewPagerAdapter
import com.lacklab.app.wallsplash.ui.viewmodels.GalleryViewModel
import com.lacklab.app.wallsplash.util.TAB_COLLECTIONS
import com.lacklab.app.wallsplash.util.TAB_PHOTOS
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class GalleryFragment : BaseFragment<FragmentGalleryBinding, GalleryViewModel>() {

    private val galleryViewModel:GalleryViewModel by viewModels()
    private var viewPagerAdapter: ViewPagerAdapter? = null
    private var tabLayoutMediator: TabLayoutMediator? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.d("onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super   .onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        Timber.d("onStart")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onViewCreated")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.d("onDetach")
    }

    override val layoutId: Int
        get() = R.layout.fragment_gallery

    override fun getVM() = galleryViewModel

    override fun bindVM(binding: FragmentGalleryBinding, vm: GalleryViewModel) {
        with(binding) {
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

//    override fun init() {
//        initView()
//    }

    override fun clear() {
        viewPagerAdapter = null
    }

    override fun clearView() {
        tabLayoutMediator?.detach()
        tabLayoutMediator = null
//        with(binding!!) {
//            viewPagerGallery.adapter = null
//        }
//        binding = null
    }

//    private fun initView() {
//        with(binding!!) {
//            // init view pager
//            viewPagerAdapter = ViewPagerAdapter(childFragmentManager, lifecycle, 2)
//            viewPagerGallery.apply {
//                adapter = viewPagerAdapter
//            }
//            // connect tab layout and view pager
//            tabLayoutMediator = TabLayoutMediator(tabsGallery, viewPagerGallery) { tab, position ->
//                when(position) {
//                    TAB_PHOTOS -> tab.text = getString(R.string.title_photos)
//                    TAB_COLLECTIONS -> tab.text = getString(R.string.title_collections)
//                }
//            }
//            tabLayoutMediator!!.attach()
//        }
//
//    }

}