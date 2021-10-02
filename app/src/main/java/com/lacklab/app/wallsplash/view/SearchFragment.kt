package com.lacklab.app.wallsplash.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lacklab.app.wallsplash.databinding.FragmentSearchBinding
import timber.log.Timber

class SearchFragment : Fragment() {

    private lateinit var viewBinding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val funName = object{}.javaClass.enclosingMethod.name
        Timber.d(funName)
        viewBinding = FragmentSearchBinding.inflate(inflater, container, false)

        return viewBinding.root
    }
}