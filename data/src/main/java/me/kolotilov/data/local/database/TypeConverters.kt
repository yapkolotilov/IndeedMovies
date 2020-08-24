package me.kolotilov.data.local.database

import androidx.room.TypeConverter
import me.kolotilov.logic.MovieType

internal class MovieTypeConverter {

    @TypeConverter
    fun movieTypeToString(movieType: MovieType): String {
        return movieType.toString()
    }

    @TypeConverter
    fun stringToMovieType(movieType: String): MovieType {
        return MovieType.valueOf(movieType)
    }
}