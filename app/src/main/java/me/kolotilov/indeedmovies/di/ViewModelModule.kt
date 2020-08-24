package me.kolotilov.indeedmovies.di

import android.content.Context
import dagger.Module
import dagger.Provides
import me.kolotilov.data.local.LocalRepository
import me.kolotilov.data.web.WebRepository
import me.kolotilov.indeedmovies.ui.login.LoginViewModel
import me.kolotilov.indeedmovies.ui.movies.movieDetails.MovieDetailsViewModel
import me.kolotilov.indeedmovies.ui.movies.movieList.MovieListViewModel
import me.kolotilov.indeedmovies.ui.movies.savedMovies.SavedMoviesViewModel
import me.kolotilov.logic.QrDecoder

@Module
class ViewModelModule(
    private val appContext: Context
) {

    @Provides
    fun provideAppContext(): Context {
        return appContext
    }

    @Provides
    fun provideLoginViewModelFactory(
        qrDecoder: QrDecoder,
        localRepository: LocalRepository
    ) : LoginViewModel.Factory {
        return LoginViewModel.Factory(qrDecoder, localRepository)
    }

    @Provides
    fun provideMovieListViewModelFactory(
        webRepository: WebRepository
    ): MovieListViewModel.Factory {
        return MovieListViewModel.Factory(webRepository)
    }

    @Provides
    fun provideMovieDetailsViewModelFactory(
        webRepository: WebRepository, localRepository: LocalRepository
    ): MovieDetailsViewModel.Factory {
        return MovieDetailsViewModel.Factory(webRepository, localRepository)
    }

    @Provides
    fun provideSavedViewModelFactory(
        localRepository: LocalRepository
    ): SavedMoviesViewModel.Factory {
        return SavedMoviesViewModel.Factory(localRepository)
    }
}