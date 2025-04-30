package com.example.imovie.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.imovie.data.model.Movie

@Database(entities = [
    Movie::class
], version = 1, exportSchema = false)
@TypeConverters(com.example.imovie.utils.TypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}