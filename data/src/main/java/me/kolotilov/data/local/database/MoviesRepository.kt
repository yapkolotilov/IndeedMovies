package me.kolotilov.data.local.database

import android.content.Context
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import me.kolotilov.logic.MovieDetails

interface MoviesRepository {

    fun saveMovie(movie: MovieDetails): Completable

    fun removeMovie(id: String): Completable

    fun getAllMovies(): Single<List<MovieDetails>>

    fun getById(movieId: String): Maybe<MovieDetails>

    fun removeAll(): Completable
}

internal class MoviesRepositoryImpl(context: Context) : MoviesRepository {

    private val appDatabase = AppDatabase.getInstance(context)

    override fun getAllMovies(): Single<List<MovieDetails>> {
        return appDatabase.movieDao.getAll()
            .map { it.map { it.toMovieDetails() } }
            .subscribeOn(Schedulers.io())
    }

    override fun saveMovie(movie: MovieDetails): Completable {
        return appDatabase.movieDao.insertMovie(movie.toMovieEntity())
            .subscribeOn(Schedulers.io())
    }

    override fun removeMovie(id: String): Completable {
        return appDatabase.movieDao.deleteMovie(id)
            .subscribeOn(Schedulers.io())
    }

    override fun getById(movieId: String): Maybe<MovieDetails> {
        return appDatabase.movieDao.getById(movieId)
            .map { it.toMovieDetails() }
            .subscribeOn(Schedulers.io())
    }

    override fun removeAll(): Completable {
        return appDatabase.movieDao.removeAll()
            .subscribeOn(Schedulers.io())
    }
}

private fun MovieDetails.toMovieEntity(): MovieEntity {
    return MovieEntity(id, title, year, runtime, genre, director, actors, plot, country,
        posterUrl, metascore, imdbRating, imdbVotes, type, production)
}

private fun MovieEntity.toMovieDetails(): MovieDetails {
    return MovieDetails(id, title, year, runtime, genre, director, actors, plot, country,
        posterUrl, metascore, imdbRating, imdbVotes, type, production)
}