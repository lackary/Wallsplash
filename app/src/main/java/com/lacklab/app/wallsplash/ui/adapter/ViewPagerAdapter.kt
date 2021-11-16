package com.lacklab.app.wallsplash.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lacklab.app.wallsplash.util.TAB_COLLECTIONS
import com.lacklab.app.wallsplash.util.TAB_PHOTOS
import com.lacklab.app.wallsplash.util.TAB_USERS
import com.lacklab.app.wallsplash.ui.view.collections.CollectionsFragment
import com.lacklab.app.wallsplash.ui.view.photos.PhotosFragment
import com.lacklab.app.wallsplash.ui.view.users.UsersFragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val pageCount: Int
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = pageCount

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            TAB_PHOTOS -> PhotosFragment()
            TAB_COLLECTIONS -> CollectionsFragment()
            TAB_USERS -> UsersFragment()
            else -> PhotosFragment()
        }
    }
}