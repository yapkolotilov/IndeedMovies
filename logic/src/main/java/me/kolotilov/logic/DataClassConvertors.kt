package me.kolotilov.logic

fun MovieDetails.toMovieSearchItem(): MovieSearchItem {
    return MovieSearchItem(
        id, title, year, type, posterUrl
    )
}