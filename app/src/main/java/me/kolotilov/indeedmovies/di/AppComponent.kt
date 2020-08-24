package me.kolotilov.indeedmovies.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import me.kolotilov.indeedmovies.App
import javax.inject.Singleton

@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    DataModule::class,
    LogicModule::class,
    ViewModelModule::class
])
@Singleton
interface AppComponent : AndroidInjector<App> {

    @Suppress("DEPRECATION")
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>() {

        abstract fun viewModelModule(module: ViewModelModule): Builder
    }
}