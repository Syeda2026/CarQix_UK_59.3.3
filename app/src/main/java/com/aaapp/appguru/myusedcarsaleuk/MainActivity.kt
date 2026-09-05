package com.aaapp.appguru.myusedcarsaleuk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.aaapp.appguru.myusedcarsaleuk.core.ads.AdManager
import com.aaapp.appguru.myusedcarsaleuk.core.utils.DataStoreManager
import com.aaapp.appguru.myusedcarsaleuk.features.buy.BuyCarsViewModel
import com.aaapp.appguru.myusedcarsaleuk.features.home.HomeViewModel
import com.aaapp.appguru.myusedcarsaleuk.features.saved.SavedViewModel
import com.aaapp.appguru.myusedcarsaleuk.features.search.SearchViewModel
import com.aaapp.appguru.myusedcarsaleuk.features.splash.UpdateViewModel
import com.aaapp.appguru.myusedcarsaleuk.features.splash.UpdateViewModelFactory
import com.aaapp.appguru.myusedcarsaleuk.ui.navigation.AppNavigation
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.UsedCarsUkTheme

import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val updateViewModel: UpdateViewModel by viewModels { UpdateViewModelFactory(applicationContext) }

    private val updateLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode != RESULT_OK) {
            // If update failed or cancelled, we can handle it here if needed
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = androidx.activity.SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = androidx.activity.SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )

        val dataStoreManager = DataStoreManager(applicationContext)

        // Initialize Firebase Analytics, Crashlytics, and Remote Config
        com.aaapp.appguru.myusedcarsaleuk.core.analytics.AnalyticsManager.initialize(this)
        com.aaapp.appguru.myusedcarsaleuk.core.analytics.CrashlyticsManager.initialize()
        com.aaapp.appguru.myusedcarsaleuk.core.config.ConfigManager.setProvider(
            com.aaapp.appguru.myusedcarsaleuk.core.config.FirebaseConfigProvider(
                onAdsConfigUpdated = { updatedConfig ->
                    com.aaapp.appguru.myusedcarsaleuk.core.config.ConfigManager.updateLocalAdsConfig(updatedConfig)
                },
                onConfigUpdated = {
                    AdManager.onConfigUpdated(this)
                }
            )
        )

        // Initialize Chrome Custom Tabs and AdMob
        com.aaapp.appguru.myusedcarsaleuk.core.utils.ChromeTabManager.init(this)
        AdManager.initialize(this)

        updateViewModel.checkForUpdates()

        setContent {
            UsedCarsUkTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    AppNavigation(
                        navController = navController,
                        dataStoreManager = dataStoreManager,
                        updateViewModel = updateViewModel,
                        updateLauncher = updateLauncher
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateViewModel.onResume()
    }
}
