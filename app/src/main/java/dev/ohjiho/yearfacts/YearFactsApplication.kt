package dev.ohjiho.yearfacts

import android.app.Application
import dev.ohjiho.yearfacts.inject.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class YearFactsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@YearFactsApplication)
            modules(viewModelModule)
        }
    }
}