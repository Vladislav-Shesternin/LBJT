package com.education.lbjt

import android.app.Application
import android.content.Context

lateinit var appContext: Context private set

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

//        MobileAds.initialize(this)
    }

}