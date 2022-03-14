package com.lacklab.app.wallsplash

import android.app.Application
import android.content.Context
import com.lacklab.app.wallsplash.log.LackLabDebugTree
import com.lacklab.app.wallsplash.log.LackLabProductionTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        if(BuildConfig.DEBUG) {
            Timber.plant(LackLabDebugTree())
        } else {
            Timber.plant(LackLabProductionTree())
        }
    }
}