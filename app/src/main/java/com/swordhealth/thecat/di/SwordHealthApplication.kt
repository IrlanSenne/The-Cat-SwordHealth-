package com.swordhealth.thecat.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class SwordHealthApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SwordHealthApplication)
            modules(appModule)
        }
    }
}