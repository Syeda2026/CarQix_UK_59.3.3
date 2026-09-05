package com.aaapp.appguru.myusedcarsaleuk.features.saved

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.aaapp.appguru.myusedcarsaleuk.data.local.AppRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SavedViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AppRepository.getInstance(application)

    val savedItems = repository.allSavedItems.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun deleteSavedItem(id: String) {
        viewModelScope.launch {
            repository.deleteSavedItem(id)
        }
    }
}
