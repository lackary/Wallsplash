package com.lacklab.app.wallsplash.ui.view.main

import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.base.BaseActivity
import com.lacklab.app.wallsplash.databinding.ActivityMainBinding
import com.lacklab.app.wallsplash.ext.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private val mainViewModel: MainViewModel by viewModels()

//    private var currentNavController: LiveData<NavController>? = null
    private var navController: NavController? = null

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
        navController = null
//        currentNavController = null
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
//        val navController = this.findNavController(R.id.nav_host_main)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(R.id.menu_bottom_gallery, R.id.menu_bottom_search)
//        )
//        // if we have the appbar, this will show the title of appbar
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        // using kotlin extension
//        val navGraphIds = listOf(
//            R.navigation.nav_gallery,
//            R.navigation.nav_search
//        )
        // keep fragment alive when using BottomNavigationView
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_main
        ) as NavHostFragment
        val navController = navHostFragment.navController
        with(binding) {
            bottomNavBar.setupWithNavController(navController)
//            val controller = bottomNavBar.setupWithNavController(
//                navGraphIds = navGraphIds,
//                fragmentManager = supportFragmentManager,
//                containerId = R.id.nav_host_main,
//                intent = intent
//            )
//            currentNavController = controller
        }
    }

    override fun onSupportNavigateUp(): Boolean =
        navController?.navigateUp() ?: false
//        currentNavController?.value?.navigateUp() ?: false

    /**
     * Overriding popBackStack is necessary in this case
     * if the app is started from the deep link.
     */
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (navController?.popBackStack() != true) {
            super.onBackPressed()
        }
//        if (currentNavController?.value?.popBackStack() != true) {
//            super.onBackPressed()
//        }
    }
}