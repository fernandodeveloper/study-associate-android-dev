package br.com.fernandodeveloper.tasklist

import android.app.Application

class GetApplication : Application() {

    companion object {
        lateinit var instance: GetApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}