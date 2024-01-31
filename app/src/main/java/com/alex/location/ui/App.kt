package com.alex.location.ui

import android.app.Application
import android.content.Context
import com.alex.location.bean.User

class App : Application() {

    companion object {
        var appContext: Context? = null
        var currentUser: User? = null
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}