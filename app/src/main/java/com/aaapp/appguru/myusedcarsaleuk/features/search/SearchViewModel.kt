package com.aaapp.appguru.myusedcarsaleuk.features.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.aaapp.appguru.myusedcarsaleuk.data.local.AppRepository
import com.aaapp.appguru.myusedcarsaleuk.model.AutomotiveDataProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AppRepository.getInstance(application)

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val recentSearches = repository.recentSearches.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun onQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun executeSearch(query: String) {
        if (query.isNotBlank()) {
            viewModelScope.launch {
                repository.addRecentSearch(query)
            }
        }
    }

    fun clearRecentSearches() {
        viewModelScope.launch {
            repository.clearRecentSearches()
        }
    }
}
