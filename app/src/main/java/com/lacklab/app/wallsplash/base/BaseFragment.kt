package com.lacklab.app.wallsplash.base

import android.content.Context
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
import com.lacklab.app.wallsplash.ext.observe
import timber.log.Timber

abstract class BaseFragment<DB: ViewDataBinding, VM: BaseViewModel > : Fragment() {

    private lateinit var viewModel: VM
    private lateinit var binding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val funName = object{}.javaClass.enclosingMethod.name
        Timber.d(funName)
        viewModel = getVM()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val funName = object{}.javaClass.enclosingMethod.name
//        Timber.d(funName)
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
//        binding!!.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val funName = object{}.javaClass.enclosingMethod.name
//        Timber.d(funName)
        bindVM(binding, viewModel)
        with(viewModel) {
//            observe(errorMessage) { msg ->
//                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
//            }
        }
//        init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        val funName = object{}.javaClass.enclosingMethod.name
//        Timber.d(funName)
        clearView()
    }

    override fun onDestroy() {
        super.onDestroy()
//        val funName = object{}.javaClass.enclosingMethod.name
//        Timber.d(funName)
        clear()
    }

    @get:LayoutRes
    abstract val layoutId: Int

    abstract fun getVM(): VM

    abstract fun bindVM(binding: DB, vm: VM)

//    abstract fun layout(): Int
//
//    abstract fun init()

    abstract fun clear()

    abstract fun clearView()

    protected open fun showToastMessage(message: String?) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
    }

    fun launchOnLifecycleScope(execute: suspend () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            execute()
        }
    }
}