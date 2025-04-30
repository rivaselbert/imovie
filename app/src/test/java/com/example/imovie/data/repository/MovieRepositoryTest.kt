package com.example.imovie.data.repository

import com.example.imovie.data.source.local.LocalMovieDataSource
import com.example.imovie.data.source.remote.RemoteMovieDataSource
import com.example.imovie.utils.MovieTestDataFactory
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryTest {

    @Mock private lateinit var remoteMovieDataSource: RemoteMovieDataSource
    @Mock private lateinit var localMovieDataSource: LocalMovieDataSource

    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        movieRepository = MovieRepository(
            remoteMovieDataSource = remoteMovieDataSource,
            localMovieDataSource = localMovieDataSource,
        )
    }

    @Test
    fun `getMovies should return success`() = runBlocking {
        val response = MovieTestDataFactory.allMovies

        val expectedResult = Result.success(response)

        Mockito.`when`(remoteMovieDataSource.getMovies()).thenReturn(response)

        val result = movieRepository.getMovies()

        verify(remoteMovieDataSource).getMovies()
        verify(localMovieDataSource).clearMovies()
        verify(localMovieDataSource).saveMovies(result.getOrNull()!!)
        assert(result.isSuccess)
        assertEquals(expectedResult, result)
    }

    @Test
    fun `getMovies should return failure`() = runBlocking {
        Mockito.`when`(remoteMovieDataSource.getMovies()).thenThrow(RuntimeException("Failed to fetch movies"))
        Mockito.`when`(localMovieDataSource.getAllMovies()).thenReturn(emptyList())

        val result = movieRepository.getMovies()

        verify(remoteMovieDataSource).getMovies()
        assert(result.isFailure)
    }
}