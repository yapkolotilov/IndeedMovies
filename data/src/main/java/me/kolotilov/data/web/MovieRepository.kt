package me.kolotilov.data.web

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import me.kolotilov.logic.MovieDetails
import me.kolotilov.logic.MovieSearchItem
import me.kolotilov.logic.MovieType

private const val ABSENT = "N/A"

interface MovieRepository {

    fun searchMovies(query: String, page: Int): Single<List<MovieSearchItem>>

    fun getMovieById(id: String): Single<MovieDetails>
}

internal class MovieRepositoryImpl(
    private val omdbApi: OmdbApi
) : MovieRepository {

    override fun searchMovies(query: String, page: Int): Single<List<MovieSearchItem>> {
        return omdbApi.searchMovies(query, page)
            .map { it.search?.map { it.toMovieSearchItem() } ?: emptyList() }
            .subscribeOn(Schedulers.io())
    }

    override fun getMovieById(id: String): Single<MovieDetails> {
        return omdbApi.getMovieById(id)
            .map { it.toMovieDetails() }
            .subscribeOn(Schedulers.io())
    }
}

private fun MovieSearchItemDto.toMovieSearchItem(): MovieSearchItem {
    return MovieSearchItem(
        id = id,
        title = title,
        year = year.toInt(),
        posterUrl = if (posterUrl != ABSENT) posterUrl else null,
        type = parseType(type)
    )
}

private fun MovieDetailsDto.toMovieDetails(): MovieDetails {
    return MovieDetails(
        id = id,
        title = title,
        year = year.toInt(),
        runtime = runtime.isPresent(),
        genre = genre.isPresent(),
        director = director.isPresent(),
        actors = actors.isPresent(),
        plot = plot.isPresent(),
        country = country.isPresent(),
        posterUrl = posterUrl.isPresent(),
        metascore = metascore.toIntOrNull(),
        imdbRating = imdbRating.toFloatOrNull(),
        imdbVotes = imdbVotes.replace(",", "").toIntOrNull(),
        type = parseType(type),
        production = production.isPresent()
    )
}

private fun parseType(type: String): MovieType? {
    return when(type) {
        "movie" -> MovieType.MOVIE
        "series" -> MovieType.SERIES
        else -> null
    }
}

private fun String.isPresent(): String? {
    return if (this != "N/A") this else null
}