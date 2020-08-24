package me.kolotilov.indeedmovies.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.kolotilov.indeedmovies.ui.login.LoginFragment
import me.kolotilov.indeedmovies.ui.movies.movieDetails.MovieDetailsFragment
import me.kolotilov.indeedmovies.ui.movies.movieList.MovieListFragment
import me.kolotilov.indeedmovies.ui.movies.savedMovies.SavedMoviesFragment

@Module
abstract class AppModule {

    @ContributesAndroidInjector
    abstract fun loginFragmentInjector(): LoginFragment

    @ContributesAndroidInjector
    abstract fun movieListFragmentInjector(): MovieListFragment

    @ContributesAndroidInjector
    abstract fun movieDetailsFragmentInjector(): MovieDetailsFragment

    @ContributesAndroidInjector
    abstract fun savedMovieFragmentInjector(): SavedMoviesFragment
}