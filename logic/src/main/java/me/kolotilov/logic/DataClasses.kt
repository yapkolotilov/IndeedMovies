package me.kolotilov.logic

data class MovieSearchItem(
    val id: String,
    val title: String,
    val year: Int?,
    val type: MovieType?,
    val posterUrl: String?
)

data class MovieDetails(
    val id: String,
    val title: String,
    val year: Int?,
    val runtime: String?,
    val genre: String?,
    val director: String?,
    val actors: String?,
    val plot: String?,
    val country: String?,
    val posterUrl: String?,
    val metascore: Int?,
    val imdbRating: Float?,
    val imdbVotes: Int?,
    val type: MovieType?,
    val production: String?
)

enum class MovieType {
    MOVIE,
    SERIES
}