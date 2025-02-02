package com.rezakur.mvexplore

import android.app.Application
import com.rezakur.mvexplore.di.databaseModule
import com.rezakur.mvexplore.di.networkModule
import com.rezakur.mvexplore.di.repositoryModule
import com.rezakur.mvexplore.di.useCaseModule
import com.rezakur.mvexplore.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MVExploreApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MVExploreApp)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                )
            )
        }
    }
}