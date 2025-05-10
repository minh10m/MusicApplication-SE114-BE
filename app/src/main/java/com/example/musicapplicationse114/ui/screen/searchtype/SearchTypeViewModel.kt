package com.example.musicapplicationse114.ui.screen.searchtype

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.musicapplicationse114.R
import com.example.musicapplicationse114.repositories.Api
import com.example.musicapplicationse114.repositories.MainLog
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class SearchTypeUiState(
    val recentSearches: List<RecentSearch> = emptyList()
)

@HiltViewModel
class SearchTypeViewModel @Inject constructor(
    private val mainLog: MainLog?,
    private val api: Api?
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        SearchTypeUiState(
            recentSearches = listOf(
                RecentSearch(R.drawable.travis, "You (feat. Travis Scott)", "Song • Don Toliver"),
                RecentSearch(R.drawable.johnwick, "John Wick: Chapter 4", "Album • Tyler Bates, Joel J. Richard"),
                RecentSearch(R.drawable.maroon5, "Maroon 5", "Artist"),
                RecentSearch(R.drawable.phonk, "Phonk Madness", "Playlist")
            )
        )
    )
    val uiState: StateFlow<SearchTypeUiState> = _uiState.asStateFlow()

    fun removeFromHistory(item: RecentSearch) {
        _uiState.value = _uiState.value.copy(
            recentSearches = _uiState.value.recentSearches.filterNot { it == item }
        )
    }

    fun clearHistory() {
        _uiState.value = _uiState.value.copy(
            recentSearches = emptyList()
        )
    }
}
