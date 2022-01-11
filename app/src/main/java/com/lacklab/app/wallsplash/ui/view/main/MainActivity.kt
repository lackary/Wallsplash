package com.lacklab.app.wallsplash.ui.view.main

import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.base.BaseActivity
import com.lacklab.app.wallsplash.databinding.ActivityMainBinding
import com.lacklab.app.wallsplash.ext.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private val mainViewModel: MainViewModel by viewModels()

    private var currentNavController: LiveData<NavController>? = null

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun getVM() = mainViewModel

    override fun bindVM(binding: ActivityMainBinding, viewModel: MainViewModel) {
    }

    override fun onStart() {
        super.onStart()
        val funName = object{}.javaClass.enclosingMethod?.name
        Timber.d(funName)
        initView()
    }

    override fun onResume() {
        super.onResume()
        val funName = object{}.javaClass.enclosingMethod?.name
        Timber.d(funName)
    }

    override fun onPause() {
        super.onPause()
        val funName = object{}.javaClass.enclosingMethod?.name
        Timber.d(funName)
    }

    override fun onStop() {
        super.onStop()
        val funName = object{}.javaClass.enclosingMethod?.name
        Timber.d(funName)
        currentNavController = null
    }

    override fun onRestart() {
        super.onRestart()
        val funName = object{}.javaClass.enclosingMethod?.name
        Timber.d(funName)
    }

    override fun onDestroy() {
        super.onDestroy()
        val funName = object{}.javaClass.enclosingMethod?.name
        Timber.d(funName)
    }

    private fun initView() {
        // init Bottom Navigation bar
//        val navController = findNavController(R.id.nav_host_main)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(R.id.menu_bottom_gallery, R.id.menu_bottom_search)
//        )
//        // if we have the appbar, this will show the title of appbar
//        setupActionBarWithNavController(navController, appBarConfiguration)
        val navGraphIds = listOf(
            R.navigation.nav_gallery,
            R.navigation.nav_search
        )
        with(binding) {
//            bottomNavBar.setupWithNavController(navController)
            val controller = bottomNavBar.setupWithNavController(
                navGraphIds = navGraphIds,
                fragmentManager = supportFragmentManager,
                containerId = R.id.nav_host_main,
                intent = intent
            )
            currentNavController = controller
        }
    }

    override fun onSupportNavigateUp(): Boolean =
        currentNavController?.value?.navigateUp() ?: false

    /**
     * Overriding popBackStack is necessary in this case
     * if the app is started from the deep link.
     */
    override fun onBackPressed() {
        if (currentNavController?.value?.popBackStack() != true) {
            super.onBackPressed()
        }
    }
}