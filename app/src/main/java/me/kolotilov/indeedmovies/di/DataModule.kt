package me.kolotilov.indeedmovies.di

import android.content.Context
import dagger.Module
import dagger.Provides
import me.kolotilov.data.local.LocalRepository
import me.kolotilov.data.local.LocalRepositoryImpl
import me.kolotilov.data.web.OmdbApi
import me.kolotilov.data.web.WebRepository
import me.kolotilov.data.web.WebRepositoryImpl
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    fun provideOmdbApi(retrofit: Retrofit): OmdbApi {
        return retrofit.create(OmdbApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWebRepository(omdbApi: OmdbApi): WebRepository {
        return WebRepositoryImpl(omdbApi)
    }

    @Provides
    @Singleton
    fun provideLocalRepository(
        context: Context
    ): LocalRepository {
        return LocalRepositoryImpl(context)
    }
}