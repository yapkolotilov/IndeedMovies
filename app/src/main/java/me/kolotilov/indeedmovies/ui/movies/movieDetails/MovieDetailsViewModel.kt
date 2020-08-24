package me.kolotilov.indeedmovies.ui.movies.movieDetails

import android.graphics.Bitmap
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

    fun saveMovie(movieDetails: MovieDetails, bitmap: Bitmap): Completable {
        val uri = localRepository.images.getFilePath(movieDetails.id)
        val details = MovieDetails(
            id = movieDetails.id,
            title = movieDetails.title,
            posterUrl = uri.toString(),
            type = movieDetails.type,
            runtime = movieDetails.runtime,
            genre = movieDetails.genre,
            actors = movieDetails.actors,
            year = movieDetails.year,
            imdbRating = movieDetails.imdbRating,
            country = movieDetails.country,
            metascore = movieDetails.metascore,
            imdbVotes = movieDetails.imdbVotes,
            director = movieDetails.director,
            production = movieDetails.production,
            plot = movieDetails.plot
        )
        return localRepository.movies.saveMovie(details)
            .mergeWith(localRepository.images.saveImage(bitmap, movieDetails.id))
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun removeMovie(movieId: String): Completable {
        return localRepository.movies.removeMovie(movieId)
            .mergeWith(localRepository.images.deleteImage(movieId))
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