package me.kolotilov.indeedmovies.ui.movies.movieDetails

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import me.kolotilov.data.local.LocalRepository
import me.kolotilov.data.web.WebRepository
import me.kolotilov.indeedmovies.ui.base.BaseViewModel
import me.kolotilov.logic.MovieDetails

class MovieDetailsViewModel(
    private val webRepository: WebRepository,
    private val localRepository: LocalRepository
) : BaseViewModel() {

    fun getMovieByIdFromWeb(id: String): Single<MovieDetails> {
        return webRepository.movies.getMovieById(id)
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMovieFromDatabase(id: String): Maybe<MovieDetails> {
        return localRepository.movies.getById(id)
    }

    fun saveMovie(movieDetails: MovieDetails): Completable {
        return localRepository.movies.saveMovie(movieDetails)
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun removeMovie(movieId: String): Completable {
        return localRepository.movies.removeMovie(movieId)
            .observeOn(AndroidSchedulers.mainThread())
    }

    class Factory(
        private val webRepository: WebRepository,
        private val localRepository: LocalRepository
    ) : BaseViewModel.Factory<MovieDetailsViewModel> {

        override fun create(): MovieDetailsViewModel {
            return MovieDetailsViewModel(webRepository, localRepository)
        }
    }
}