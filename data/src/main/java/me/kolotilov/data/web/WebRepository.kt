package me.kolotilov.data.web

interface WebRepository {

    val movies: MovieRepository
}

class WebRepositoryImpl(
    omdbApi: OmdbApi
) : WebRepository {

    override val movies: MovieRepository = MovieRepositoryImpl(omdbApi)
}