package com.lacklab.app.wallsplash.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<DB: ViewDataBinding, VM: BaseViewModel> : AppCompatActivity() {

    private lateinit var _binding: DB
    val binding: DB
        get() = _binding
    private lateinit var _viewModel: VM
    val viewModel: VM
        get() = _viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutId)
        _viewModel = getVM()
        bindVM(binding, viewModel)
    }

    @get:LayoutRes
    abstract val layoutId: Int

    abstract fun getVM(): VM

    abstract fun bindVM(binding: DB, viewModel: VM)
}