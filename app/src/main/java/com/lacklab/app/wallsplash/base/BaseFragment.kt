package com.lacklab.app.wallsplash.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import timber.log.Timber

abstract class BaseFragment<DB: ViewDataBinding, VM: BaseViewModel > : Fragment() {

    private lateinit var _viewModel: VM
    val viewModel: VM
        get() = _viewModel
    private lateinit var _binding: DB
    val binding: DB
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModel = getVM()
        init()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindVM(binding, viewModel)
        with(viewModel) {
        }
    }

    override fun onStart() {
        super.onStart()

    }



    override fun onDestroyView() {
        super.onDestroyView()
        clearView()
    }

    override fun onDestroy() {
        super.onDestroy()
        clear()
    }

    @get:LayoutRes
    abstract val layoutId: Int

    abstract fun getVM(): VM

    abstract fun bindVM(binding: DB, vm: VM)

    abstract fun init()

    abstract fun clear()

    abstract fun clearView()

    protected open fun showToastMessage(message: String?) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
    }

    fun launchOnLifecycleScope(execute: suspend () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            Timber.d("launchOnLifecycleScope")
            execute()
        }
    }
}