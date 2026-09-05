package com.aaapp.appguru.myusedcarsaleuk.features.splash

import android.app.Activity
import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aaapp.appguru.myusedcarsaleuk.core.utils.AppUpdateWrapper
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.install.model.InstallStatus
import kotlinx.coroutines.flow.StateFlow

class UpdateViewModel(context: Context) : ViewModel() {
    private val appUpdateWrapper = AppUpdateWrapper(context)

    val updateInfo: StateFlow<AppUpdateInfo?> = appUpdateWrapper.updateInfo
    val isChecking: StateFlow<Boolean> = appUpdateWrapper.isChecking
    val installStatus: StateFlow<Int> = appUpdateWrapper.installStatus
    val downloadProgress: StateFlow<Float> = appUpdateWrapper.downloadProgress

    fun checkForUpdates() {
        appUpdateWrapper.checkForUpdates()
    }

    fun startUpdate(activity: Activity, launcher: ActivityResultLauncher<IntentSenderRequest>, isImmediate: Boolean = false) {
        appUpdateWrapper.startUpdate(activity, launcher, isImmediate)
    }

    fun completeUpdate() {
        appUpdateWrapper.completeUpdate()
    }

    fun onResume() {
        appUpdateWrapper.onResume()
    }

    override fun onCleared() {
        super.onCleared()
        appUpdateWrapper.unregisterListener()
    }
}

class UpdateViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpdateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UpdateViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
