package com.aaapp.appguru.myusedcarsaleuk.features.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.aaapp.appguru.myusedcarsaleuk.data.local.AppRepository
import com.aaapp.appguru.myusedcarsaleuk.model.AutomotiveDataProvider
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AppRepository.getInstance(application)

    val recentActivities = repository.recentActivities.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val savedItems = repository.allSavedItems.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val featuredGuides = AutomotiveDataProvider.articles.filter { it.isFeatured }
}
