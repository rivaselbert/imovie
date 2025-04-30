package com.example.imovie.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imovie.data.model.Movie
import com.example.imovie.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieUIState())
    val uiState = _uiState.asStateFlow()

    private val searchText = MutableStateFlow<String?>(null)

    init {
        getMovies()

        viewModelScope.launch {
            // Debounce search input to reduce unnecessary API calls
            searchText.debounce(timeoutMillis = 500)
                .collect { searchText ->
                    searchText?.let {
                        searchMovies(it)
                    }
                }
        }
    }

    fun getMovies() {
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

    private fun searchMovies(searchText: String) {
        viewModelScope.launch {
            movieRepository.searchMovies(searchText)
                .onSuccess { movies ->
                    _uiState.value = _uiState.value.copy(movies = movies)
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        error = error.localizedMessage ?: "Oops, something went wrong."
                    )
                }
        }
    }

    /**
     * Updates the search text state and triggers the search with debounce mechanism.
     * After the debounce period, `searchMovies(searchText)` is called to perform the search.
     */
    fun updateSearchText(searchText: String) {
        this.searchText.value = searchText
    }

    private fun setIsLoading(isLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = isLoading)
    }
}

data class MovieUIState(
    val movies: List<Movie>? = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)