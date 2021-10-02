package com.lacklab.app.wallsplash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lacklab.app.wallsplash.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val funName = object{}.javaClass.enclosingMethod.name
        Log.v(TAG, "test $funName")
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val navView: BottomNavigationView = viewBinding.bottomNavBar
        val navController = findNavController(R.id.nav_host)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_photo_library, R.id.navigation_image_search)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
//        setContentView(R.layout.activity_main)

//        binding.txtViewHello.text = "shit lift"
    }

    override fun onStart() {
        super.onStart()
        Log.v(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.v(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.v(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG, "onStop")
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG, "onDestroy")
    }
}