package com.github.krottv.tmstemp.data.permissions

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StoragePermissionCheckerImpl(private val activity: ComponentActivity) :
    StoragePermissionChecker {

    private val _storagePermission = MutableStateFlow(hasPermission())

    override val storagePermission: StateFlow<PermissionState>
        get() = _storagePermission

    private val requestPermissionLauncher =
        activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            _storagePermission.value = when {
                isGranted -> PermissionState.HAS_PERMISSION
                activity.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> PermissionState.REJECTED_ASK_AGAIN
                else -> PermissionState.REJECTED_FOREVER
            }
        }

    override fun startPermissionDialog() {
        requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun hasPermission(): PermissionState {
        return if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) PermissionState.HAS_PERMISSION else PermissionState.NO_PERMISSION
    }
}