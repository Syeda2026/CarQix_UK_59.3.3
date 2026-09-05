package com.aaapp.appguru.myusedcarsaleuk.core.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppUpdateWrapper(private val context: Context) {

    private val appUpdateManager: AppUpdateManager = AppUpdateManagerFactory.create(context)
    
    private val _updateInfo = MutableStateFlow<AppUpdateInfo?>(null)
    val updateInfo: StateFlow<AppUpdateInfo?> = _updateInfo.asStateFlow()

    private val _isChecking = MutableStateFlow(false)
    val isChecking: StateFlow<Boolean> = _isChecking.asStateFlow()

    private val _installStatus = MutableStateFlow<Int>(InstallStatus.UNKNOWN)
    val installStatus: StateFlow<Int> = _installStatus.asStateFlow()

    private val _downloadProgress = MutableStateFlow(0f)
    val downloadProgress: StateFlow<Float> = _downloadProgress.asStateFlow()

    private val installStateUpdatedListener = InstallStateUpdatedListener { state ->
        _installStatus.value = state.installStatus()
        if (state.installStatus() == InstallStatus.DOWNLOADING) {
            val bytesDownloaded = state.bytesDownloaded()
            val totalBytesToDownload = state.totalBytesToDownload()
            if (totalBytesToDownload > 0) {
                _downloadProgress.value = bytesDownloaded.toFloat() / totalBytesToDownload
            }
        }
    }

    init {
        appUpdateManager.registerListener(installStateUpdatedListener)
    }

    fun checkForUpdates() {
        _isChecking.value = true
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            _isChecking.value = false
            if (info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                _updateInfo.value = info
            } else if (info.installStatus() == InstallStatus.DOWNLOADED) {
                _installStatus.value = InstallStatus.DOWNLOADED
            }
        }.addOnFailureListener {
            _isChecking.value = false
        }
    }

    fun startUpdate(activity: Activity, launcher: ActivityResultLauncher<IntentSenderRequest>, isImmediate: Boolean = false) {
        val info = _updateInfo.value ?: return
        val updateType = if (isImmediate) AppUpdateType.IMMEDIATE else AppUpdateType.FLEXIBLE
        
        if (info.isUpdateTypeAllowed(updateType)) {
            try {
                appUpdateManager.startUpdateFlowForResult(
                    info,
                    launcher,
                    AppUpdateOptions.newBuilder(updateType).build()
                )
            } catch (e: Exception) {
                openPlayStore(activity)
            }
        } else {
            openPlayStore(activity)
        }
    }

    fun completeUpdate() {
        appUpdateManager.completeUpdate()
    }

    fun unregisterListener() {
        appUpdateManager.unregisterListener(installStateUpdatedListener)
    }

    private fun openPlayStore(context: Context) {
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${context.packageName}")))
        } catch (e: Exception) {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=${context.packageName}")))
        }
    }
    
    fun onResume() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            if (info.installStatus() == InstallStatus.DOWNLOADED) {
                _installStatus.value = InstallStatus.DOWNLOADED
            }
            // For immediate updates, if it was in progress, it should resume automatically
            if (info.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                // If an immediate update is already in progress, resume it.
                // In Compose/Modern Android, this might need different handling if we started it.
            }
        }
    }
}
