package me.kolotilov.data.web

import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {

    @GET("?apikey=$API_KEY")
    fun searchMovies(@Query("s") query: String, @Query("page") page: Int): Single<MovieSearchDto>

    @GET("?apiKey=$API_KEY")
    fun getMovieById(@Query("i") movieId: String): Single<MovieDetailsDto>
}

data class MovieSearchDto(
    @SerializedName("Search")
    val search: List<MovieSearchItemDto>? = null,
    @SerializedName("totalResults")
    val totalResults: Int
)

data class MovieSearchItemDto(
    @SerializedName("imdbID")
    val id: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Poster")
    val posterUrl: String
)

data class MovieDetailsDto(
    @SerializedName("imdbID")
    val id: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    @SerializedName("Runtime")
    val runtime: String,
    @SerializedName("Genre")
    val genre: String,
    @SerializedName("Director")
    val director: String,
    @SerializedName("Actors")
    val actors: String,
    @SerializedName("Plot")
    val plot: String,
    @SerializedName("Country")
    val country: String,
    @SerializedName("Poster")
    val posterUrl: String,
    @SerializedName("Metascore")
    val metascore: String,
    @SerializedName("imdbRating")
    val imdbRating: String,
    @SerializedName("imdbVotes")
    val imdbVotes: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Production")
    val production: String
)

private const val API_KEY = "795a9616"