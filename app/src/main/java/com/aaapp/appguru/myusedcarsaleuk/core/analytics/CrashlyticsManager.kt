package com.aaapp.appguru.myusedcarsaleuk.core.analytics

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics

object CrashlyticsManager {

    private const val TAG = "CrashlyticsManager"

    fun initialize() {
        try {
            val crashlytics = FirebaseCrashlytics.getInstance()
            crashlytics.setCrashlyticsCollectionEnabled(true)
            crashlytics.setCustomKey("app_platform", "Android_Compose")
            crashlytics.setCustomKey("app_market", "UK_Automotive")
            Log.d(TAG, "Firebase Crashlytics initialized.")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize Crashlytics", e)
        }
    }

    fun setCustomKey(key: String, value: String) {
        try {
            FirebaseCrashlytics.getInstance().setCustomKey(key, value)
        } catch (e: Exception) {
            Log.w(TAG, "Failed to set Crashlytics key: $key", e)
        }
    }

    fun log(message: String) {
        try {
            FirebaseCrashlytics.getInstance().log(message)
            Log.d(TAG, "[Crashlytics Log] $message")
        } catch (e: Exception) {
            Log.w(TAG, "Failed to log Crashlytics message", e)
        }
    }

    fun recordException(throwable: Throwable) {
        try {
            FirebaseCrashlytics.getInstance().recordException(throwable)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to record Crashlytics exception", e)
        }
    }
}
