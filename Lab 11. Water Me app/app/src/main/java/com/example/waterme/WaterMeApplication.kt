package com.example.waterme

import android.app.Application
import com.example.waterme.data.AppContainer
import com.example.waterme.data.DefaultAppContainer

class WaterMeApplication : Application() {

    /** Container used by the rest of the app to obtain dependencies. */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}
