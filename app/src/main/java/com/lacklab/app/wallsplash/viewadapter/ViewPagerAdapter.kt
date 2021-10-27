package com.lacklab.app.wallsplash.viewadapter

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lacklab.app.wallsplash.util.TAB_COLLECTIONS
import com.lacklab.app.wallsplash.util.TAB_PHOTOS
import com.lacklab.app.wallsplash.util.TAB_USERS
import com.lacklab.app.wallsplash.view.CollectionsFragment
import com.lacklab.app.wallsplash.view.PhotosFragment
import com.lacklab.app.wallsplash.view.UsersFragment

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