package com.pthw.food.common.composable.permission

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.delay

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RequestNotificationPermissionDialog() {
    val permissionState = rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
    var isGranted by remember { mutableStateOf<Boolean?>(null) }

    LaunchedEffect(Unit) {
        delay(3000)
        isGranted = permissionState.status.isGranted
    }

    if (isGranted == false && !permissionState.status.shouldShowRationale) {
        PermissionDialog { permissionState.launchPermissionRequest() }
    }
}