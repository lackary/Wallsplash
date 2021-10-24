package com.lacklab.app.wallsplash.view

import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.base.BaseFragment
import com.lacklab.app.wallsplash.databinding.FragmentCollectionsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionsFragment : BaseFragment<FragmentCollectionsBinding>() {
    override fun layout(): Int = R.layout.fragment_collections

    override fun init() {

    }
}