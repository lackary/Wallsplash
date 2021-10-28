package com.lacklab.app.wallsplash.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.databinding.ActivityMainBinding
import com.lacklab.app.wallsplash.ext.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val funName = object{}.javaClass.enclosingMethod.name
        Timber.d(funName)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

//        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//            if (!task.isSuccessful) {
//                Timber.w("Fetching FCM registration token failed %s", task.exception)
//                return@OnCompleteListener
//            }
//
//            // Get new FCM registration token
//            val token = task.result
//
//            // Log and toast
//            val msg = getString(R.string.msg_token_fmt, token)
//            Timber.d("msg: $msg")
//            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//        })

//        setContentView(R.layout.activity_main)

    }

    override fun onStart() {
        super.onStart()
        val funName = object{}.javaClass.enclosingMethod.name
        Timber.d(funName)
        initView()
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

    override fun onRestart() {
        super.onRestart()
        val funName = object{}.javaClass.enclosingMethod.name
        Timber.d(funName)
    }

    override fun onDestroy() {
        super.onDestroy()
        val funName = object{}.javaClass.enclosingMethod.name
        Timber.d(funName)
    }

    private fun initView() {
        // init Bottom Navigation bar
//        val navController = findNavController(R.id.nav_host_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.menu_bottom_gallery, R.id.menu_bottom_search)
        )
        val navGraphIds = listOf(
            R.navigation.nav_gallery,
            R.navigation.nav_search
        )
        with(viewBinding) {
//            bottomNavBar.setupWithNavController(navController)
            val controller =bottomNavBar.setupWithNavController(
                navGraphIds = navGraphIds,
                fragmentManager = supportFragmentManager,
                containerId = R.id.nav_host_main,
                intent = intent
            )
            currentNavController = controller

        }
        // if we have the appbar, this will show the title of appbar
//        setupActionBarWithNavController(navController, appBarConfiguration)
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