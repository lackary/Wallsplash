package com.lacklab.app.wallsplash.ui.view

import androidx.fragment.app.viewModels
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.base.BaseFragment
import com.lacklab.app.wallsplash.databinding.FragmentUsersBinding
import com.lacklab.app.wallsplash.ui.viewmodels.UsersViewModel

class UsersFragment : BaseFragment<FragmentUsersBinding, UsersViewModel>() {

    private val usersViewModel: UsersViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.fragment_users

    override fun getVM() = usersViewModel

    override fun bindVM(binding: FragmentUsersBinding, vm: UsersViewModel) {
        with(binding) {

        }
    }

//    override fun layout() = R.layout.fragment_users
//
//    override fun init() {
//
//    }

    override fun clear() {

    }

    override fun clearView() {
//        binding = null
    }
}