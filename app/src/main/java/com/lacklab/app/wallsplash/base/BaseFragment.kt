package com.lacklab.app.wallsplash.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import timber.log.Timber

abstract class BaseFragment<Binding: ViewDataBinding> : Fragment() {

    protected var binding: Binding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val funName = object{}.javaClass.enclosingMethod.name
        Timber.d(funName)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val funName = object{}.javaClass.enclosingMethod.name
        Timber.d(funName)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val funName = object{}.javaClass.enclosingMethod.name
        Timber.d(funName)
        binding = DataBindingUtil.inflate(inflater, layout(), container, false)
        binding!!.lifecycleOwner = viewLifecycleOwner
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val funName = object{}.javaClass.enclosingMethod.name
        Timber.d(funName)
        init()
    }

    override fun onStart() {
        super.onStart()
        val funName = object{}.javaClass.enclosingMethod.name
        Timber.d(funName)
    }

    override fun onResume() {
        super.onResume()
        val funName = object{}.javaClass.enclosingMethod.name
        Timber.d(funName)
    }

    override fun onPause() {
        super.onPause()
        val funName = object{}.javaClass.enclosingMethod.name
        Timber.d(funName)
    }

    override fun onStop() {
        super.onStop()
        val funName = object{}.javaClass.enclosingMethod.name
        Timber.d(funName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val funName = object{}.javaClass.enclosingMethod.name
        Timber.d(funName)
        clearView()
    }

    override fun onDestroy() {
        super.onDestroy()
        val funName = object{}.javaClass.enclosingMethod.name
        Timber.d(funName)
        clear()
    }

    override fun onDetach() {
        super.onDetach()
        val funName = object{}.javaClass.enclosingMethod.name
        Timber.d(funName)
    }


    abstract fun layout(): Int

    abstract fun init()

    abstract fun clear()

    abstract fun clearView()

    protected open fun showToastMessage(message: String?) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
    }
}