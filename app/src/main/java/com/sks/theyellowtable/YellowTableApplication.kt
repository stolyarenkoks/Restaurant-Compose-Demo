package com.sks.theyellowtable

import android.app.Application
import com.sks.theyellowtable.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class YellowTableApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidLogger()
            androidContext(this@YellowTableApplication)
            modules(appModules)
        }
    }
}
