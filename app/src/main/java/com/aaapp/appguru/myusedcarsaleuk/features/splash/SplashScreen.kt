package com.aaapp.appguru.myusedcarsaleuk.features.splash

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaapp.appguru.myusedcarsaleuk.R
import com.aaapp.appguru.myusedcarsaleuk.common.CarQixPlayStoreLogo
import com.aaapp.appguru.myusedcarsaleuk.core.config.ConfigManager
import com.aaapp.appguru.myusedcarsaleuk.core.utils.DataStoreManager
import com.google.android.play.core.install.model.InstallStatus
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.ErrorRed
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.NavyDark
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.RoyalBlue
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.SurfaceWhite
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.TextSecondaryLight
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    dataStoreManager: DataStoreManager,
    updateViewModel: UpdateViewModel,
    updateLauncher: ActivityResultLauncher<IntentSenderRequest>,
    onNavigateOnboarding: () -> Unit,
    onNavigateHome: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var checkTrigger by remember { mutableIntStateOf(0) }
    var showNoInternetDialog by remember { mutableStateOf(false) }

    val updateInfo by updateViewModel.updateInfo.collectAsState()
    val isCheckingUpdate by updateViewModel.isChecking.collectAsState()
    val installStatus by updateViewModel.installStatus.collectAsState()
    val downloadProgress by updateViewModel.downloadProgress.collectAsState()
    
    var showUpdateDialog by remember { mutableStateOf(false) }

    LaunchedEffect(updateInfo) {
        if (updateInfo != null) {
            showUpdateDialog = true
        }
    }

    LaunchedEffect(checkTrigger, updateInfo, showUpdateDialog, isCheckingUpdate) {
        if (showUpdateDialog || isCheckingUpdate) return@LaunchedEffect

        showNoInternetDialog = false
        delay(800) // Initial delay to show brand logo
        
        val isConnected = checkInternetConnection(context)
        if (!isConnected) {
            showNoInternetDialog = true
        } else {
            // Only proceed if no update is pending OR user dismissed it
            if (updateInfo == null) {
                val isOnboardingCompleted = dataStoreManager.isOnboardingCompleted.first()
                if (isOnboardingCompleted) {
                    onNavigateHome()
                } else {
                    onNavigateOnboarding()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            // Official App Icon Emblem (without animation)
            Surface(
                color = RoyalBlue,
                shape = CircleShape,
                modifier = Modifier.size(150.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.app_logo),
                        contentDescription = "CarQix UK App Icon",
                        modifier = Modifier.size(110.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Brand Title "CarQix UK"
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "CarQix",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    color = NavyDark,
                    letterSpacing = (-0.5).sp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Surface(
                    color = ErrorRed,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "UK",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Your UK Automotive Hub",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextSecondaryLight
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Initializing....",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = RoyalBlue
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "v${com.aaapp.appguru.myusedcarsaleuk.BuildConfig.VERSION_NAME} • Verified Guide",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = TextSecondaryLight
            )
        }

        if (showUpdateDialog && updateInfo != null) {
            val isDownloading = installStatus == InstallStatus.DOWNLOADING
            val isDownloaded = installStatus == InstallStatus.DOWNLOADED
            val isMandatory = (updateInfo?.updatePriority() ?: 0) >= 4

            AlertDialog(
                onDismissRequest = { 
                    if (!isMandatory && !isDownloaded && !isDownloading) {
                        showUpdateDialog = false
                    }
                },
                shape = RoundedCornerShape(24.dp),
                containerColor = SurfaceWhite,
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                        Surface(
                            color = RoyalBlue.copy(alpha = 0.1f),
                            shape = CircleShape,
                            modifier = Modifier.size(60.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.DirectionsCar,
                                    contentDescription = null,
                                    tint = RoyalBlue,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = if (isDownloaded) "Update Ready!" else "New Update Available!",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = NavyDark,
                            textAlign = TextAlign.Center
                        )
                    }
                },
                text = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = if (isDownloaded) 
                                "The update has been downloaded. Restart the app to apply changes and enjoy the latest features."
                                else "A new version of CarQix UK is available. Update now to get the latest features, improvements, and bug fixes.",
                            fontSize = 14.sp,
                            color = TextSecondaryLight,
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Version Badge
                        Surface(
                            color = NavyDark.copy(alpha = 0.05f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "v${com.aaapp.appguru.myusedcarsaleuk.BuildConfig.VERSION_NAME} → Build ${updateInfo?.availableVersionCode() ?: "New"}",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = NavyDark
                            )
                        }

                        if (isDownloading) {
                            Spacer(modifier = Modifier.height(20.dp))
                            LinearProgressIndicator(
                                progress = { downloadProgress },
                                modifier = Modifier.fillMaxWidth().height(8.dp),
                                color = RoyalBlue,
                                trackColor = RoyalBlue.copy(alpha = 0.1f),
                                strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Downloading: ${(downloadProgress * 100).toInt()}%",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = RoyalBlue
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (isDownloaded) {
                                updateViewModel.completeUpdate()
                            } else {
                                val activity = context as? Activity
                                if (activity != null) {
                                    updateViewModel.startUpdate(activity, updateLauncher, isImmediate = false)
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isDownloading
                    ) {
                        Text(
                            text = if (isDownloaded) "Restart to Install" else "Update Now",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                },
                dismissButton = {
                    if (!isDownloaded && !isDownloading && !isMandatory) {
                        TextButton(
                            onClick = {
                                showUpdateDialog = false
                                // Continue to next screen
                                coroutineScope.launch {
                                    val isOnboardingCompleted = dataStoreManager.isOnboardingCompleted.first()
                                    if (isOnboardingCompleted) {
                                        onNavigateHome()
                                    } else {
                                        onNavigateOnboarding()
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Maybe Later",
                                fontWeight = FontWeight.SemiBold,
                                color = TextSecondaryLight,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            )
        }

        if (showNoInternetDialog) {
            AlertDialog(
                onDismissRequest = { /* Force user to retry or connect */ },
                shape = RoundedCornerShape(20.dp),
                containerColor = SurfaceWhite,
                title = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Surface(
                            color = ErrorRed.copy(alpha = 0.12f),
                            shape = CircleShape,
                            modifier = Modifier.size(56.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.WifiOff,
                                    contentDescription = "No Internet",
                                    tint = ErrorRed,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No Internet Connection",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = NavyDark,
                            textAlign = TextAlign.Center
                        )
                    }
                },
                text = {
                    Text(
                        text = "Internet connection is required to launch CarQix UK. Please enable Wi-Fi or Mobile Data and try again.",
                        fontSize = 13.5.sp,
                        color = TextSecondaryLight,
                        textAlign = TextAlign.Center,
                        lineHeight = 19.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                confirmButton = {
                    Button(
                        onClick = { checkTrigger++ },
                        colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Retry Connection",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.5.sp,
                            color = Color.White
                        )
                    }
                },
                dismissButton = {
                    OutlinedButton(
                        onClick = {
                            try {
                                val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS).apply {
                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                }
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                // Fallback to general settings if wireless settings intent fails
                                val intent = Intent(Settings.ACTION_SETTINGS).apply {
                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                }
                                context.startActivity(intent)
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Network Settings",
                            fontWeight = FontWeight.SemiBold,
                            color = NavyDark,
                            fontSize = 12.5.sp
                        )
                    }
                }
            )
        }
    }
}

private fun checkInternetConnection(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        ?: return false
    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}
