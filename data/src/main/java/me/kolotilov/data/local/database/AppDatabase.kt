package me.kolotilov.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MovieEntity::class], version = 1)
@TypeConverters(MovieTypeConverter::class)
internal abstract class AppDatabase : RoomDatabase() {

    internal abstract val movieDao: MovieDao

    companion object {

        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "AppDatabase")
                .build()
        }
    }
}