package com.aaapp.appguru.myusedcarsaleuk.core.analytics

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics

object AnalyticsManager {

    private const val TAG = "AnalyticsManager"
    private var firebaseAnalytics: FirebaseAnalytics? = null

    fun initialize(context: Context) {
        try {
            firebaseAnalytics = FirebaseAnalytics.getInstance(context.applicationContext)
            Log.d(TAG, "Firebase Analytics initialized successfully.")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize Firebase Analytics", e)
        }
    }

    fun logScreenView(screenName: String, screenClass: String = screenName) {
        try {
            val bundle = Bundle().apply {
                putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
                putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass)
            }
            firebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
            Log.d(TAG, "Logged screen view: $screenName")
        } catch (e: Exception) {
            Log.e(TAG, "Error logging screen view", e)
        }
    }

    fun logPartnerClick(
        partnerId: String,
        partnerName: String,
        category: String,
        destinationUrl: String
    ) {
        try {
            val bundle = Bundle().apply {
                putString("partner_id", partnerId)
                putString("partner_name", partnerName)
                putString("category", category)
                putString("destination_url", destinationUrl)
            }
            firebaseAnalytics?.logEvent("partner_click", bundle)
            Log.d(TAG, "Logged partner click: $partnerName ($category)")
        } catch (e: Exception) {
            Log.e(TAG, "Error logging partner click", e)
        }
    }

    fun logCarSearch(
        make: String,
        model: String,
        maxPrice: Int,
        minYear: Int,
        fuelType: String = "",
        transmission: String = ""
    ) {
        try {
            val bundle = Bundle().apply {
                putString(FirebaseAnalytics.Param.SEARCH_TERM, "$make $model".trim())
                putString("make", make)
                putString("model", model)
                putInt("max_price", maxPrice)
                putInt("min_year", minYear)
                if (fuelType.isNotBlank()) putString("fuel_type", fuelType)
                if (transmission.isNotBlank()) putString("transmission", transmission)
            }
            firebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SEARCH, bundle)
        } catch (e: Exception) {
            Log.e(TAG, "Error logging search event", e)
        }
    }

    fun logToolUsage(toolName: String, parameter: String = "") {
        try {
            val bundle = Bundle().apply {
                putString("tool_name", toolName)
                if (parameter.isNotBlank()) putString("parameter", parameter)
            }
            firebaseAnalytics?.logEvent("tool_used", bundle)
        } catch (e: Exception) {
            Log.e(TAG, "Error logging tool usage", e)
        }
    }

    fun logValuation(make: String, model: String, year: Int, mileage: Int, valuation: Int) {
        try {
            val bundle = Bundle().apply {
                putString("make", make)
                putString("model", model)
                putInt("year", year)
                putInt("mileage", mileage)
                putInt("estimated_value_gbp", valuation)
            }
            firebaseAnalytics?.logEvent("car_valuation_calculated", bundle)
        } catch (e: Exception) {
            Log.e(TAG, "Error logging valuation", e)
        }
    }

    fun logFinanceCalculation(price: Double, deposit: Double, termMonths: Int, monthly: Double) {
        try {
            val bundle = Bundle().apply {
                putDouble("vehicle_price", price)
                putDouble("deposit_amount", deposit)
                putInt("term_months", termMonths)
                putDouble("monthly_payment_gbp", monthly)
            }
            firebaseAnalytics?.logEvent("finance_calculated", bundle)
        } catch (e: Exception) {
            Log.e(TAG, "Error logging finance calculation", e)
        }
    }

    fun logCarSaved(carId: String, title: String, price: String, isSaved: Boolean) {
        try {
            val bundle = Bundle().apply {
                putString(FirebaseAnalytics.Param.ITEM_ID, carId)
                putString(FirebaseAnalytics.Param.ITEM_NAME, title)
                putString("price", price)
                putBoolean("is_saved", isSaved)
            }
            val eventName = if (isSaved) "car_saved_favorite" else "car_removed_favorite"
            firebaseAnalytics?.logEvent(eventName, bundle)
        } catch (e: Exception) {
            Log.e(TAG, "Error logging car save event", e)
        }
    }

    fun logAdEvent(adType: String, placement: String, action: String) {
        try {
            val bundle = Bundle().apply {
                putString("ad_type", adType) // banner, interstitial, native
                putString("ad_placement", placement)
                putString("action", action) // impression, click, dismissal
            }
            firebaseAnalytics?.logEvent("ad_interaction", bundle)
        } catch (e: Exception) {
            Log.e(TAG, "Error logging ad event", e)
        }
    }
}
