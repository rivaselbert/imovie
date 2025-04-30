package com.example.imovie.di

import android.content.Context
import androidx.room.Room
import com.example.imovie.data.source.local.AppDatabase
import com.example.imovie.data.source.local.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideMovieDao(database: AppDatabase): MovieDao {
        return database.movieDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "imovie.db"
        ).build()
    }
}