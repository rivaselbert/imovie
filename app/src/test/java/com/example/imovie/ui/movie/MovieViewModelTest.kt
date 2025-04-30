package com.example.imovie.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.imovie.data.repository.MovieRepository
import com.example.imovie.utils.MovieTestDataFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    @get:Rule val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock private lateinit var movieRepository: MovieRepository

    private lateinit var viewModel: MovieViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        viewModel = MovieViewModel(
            movieRepository = movieRepository,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getMovies successful`() = runTest {
        val expectedResult = MovieTestDataFactory.allMovies

        Mockito.`when`(movieRepository.getMovies()).thenReturn(Result.success(expectedResult))

        viewModel.getMovies()

        advanceUntilIdle()

        assertEquals(expectedResult, viewModel.uiState.value.movies)
    }

    @Test
    fun `getMovies failure`() = runTest {
        val errorMessage = "Failed to fetch movies"

        Mockito.`when`(movieRepository.getMovies()).thenReturn(Result.failure(Exception(errorMessage)))

        viewModel.getMovies()

        advanceUntilIdle()

        assertEquals(errorMessage, viewModel.uiState.value.error)
    }
}