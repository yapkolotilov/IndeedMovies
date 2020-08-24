package me.kolotilov.data.local.database

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import me.kolotilov.logic.MovieType

@Entity(tableName = "Movies")
internal data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "Id")
    val id: String,
    @ColumnInfo(name = "Title")
    val title: String,
    @ColumnInfo(name = "Year")
    val year: Int?,
    @ColumnInfo(name = "Runtime")
    val runtime: String?,
    @ColumnInfo(name = "Genre")
    val genre: String?,
    @ColumnInfo(name = "Director")
    val director: String?,
    @ColumnInfo(name = "Actors")
    val actors: String?,
    @ColumnInfo(name = "Plot")
    val plot: String?,
    @ColumnInfo(name = "Country")
    val country: String?,
    @ColumnInfo(name = "PosterUrl")
    val posterUrl: String?,
    @ColumnInfo(name = "Metascore")
    val metascore: Int?,
    @ColumnInfo(name = "ImdbRating")
    val imdbRating: Float?,
    @ColumnInfo(name = "ImdbVotes")
    val imdbVotes: Int?,
    @ColumnInfo(name = "Type")
    val type: MovieType?,
    @ColumnInfo(name = "Production")
    val production: String?
)

@Dao
internal interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieEntity): Completable

    @Query("DELETE FROM Movies WHERE Id = :id")
    fun deleteMovie(id: String): Completable

    @Query("SELECT * FROM Movies")
    fun getAll(): Single<List<MovieEntity>>

    @Query("SELECT * FROM Movies WHERE Id = :movieId")
    fun getById(movieId: String): Maybe<MovieEntity>
}