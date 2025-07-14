package org.catsproject.project

import android.app.Application
import org.catsproject.project.core.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class CatsApp:Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin{
            androidLogger(Level.DEBUG)
            androidContext(this@CatsApp)
        }
    }
}