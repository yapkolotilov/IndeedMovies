package me.kolotilov.indeedmovies

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import me.kolotilov.indeedmovies.di.DaggerAppComponent
import me.kolotilov.indeedmovies.di.ViewModelModule
import timber.log.Timber
import javax.inject.Inject

class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        installTimber()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
       return DaggerAppComponent.builder()
           .viewModelModule(ViewModelModule(this))
           .create(this)
    }

    private fun installTimber() {
        Timber.plant(Timber.DebugTree())
    }
}