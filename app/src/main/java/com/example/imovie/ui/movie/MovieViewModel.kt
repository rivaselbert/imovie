package com.example.imovie.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imovie.data.model.Movie
import com.example.imovie.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieUIState())
    val uiState = _uiState.asStateFlow()

    init {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch {
            setIsLoading(true)
            movieRepository.getMovies()
                .onSuccess { movies ->
                    _uiState.value = _uiState.value.copy(movies = movies)
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        error = error.localizedMessage ?: "Oops, something went wrong."
                    )
                }

            setIsLoading(false)
        }
    }

    private fun setIsLoading(isLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = isLoading)
    }
}

data class MovieUIState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)