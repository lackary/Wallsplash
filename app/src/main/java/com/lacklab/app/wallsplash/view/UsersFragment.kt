package com.lacklab.app.wallsplash.view

import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.base.BaseFragment
import com.lacklab.app.wallsplash.databinding.FragmentUsersBinding

class UsersFragment : BaseFragment<FragmentUsersBinding>() {
    override fun layout() = R.layout.fragment_users

    override fun init() {

    }

    override fun clear() {
        binding = null
    }
}