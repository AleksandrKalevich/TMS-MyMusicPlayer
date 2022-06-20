package com.github.krottv.tmstemp.data.permissions

import kotlinx.coroutines.flow.StateFlow

interface StoragePermissionChecker {

    val storagePermission: StateFlow<PermissionState>

    fun startPermissionDialog()
}

enum class PermissionState {
    HAS_PERMISSION, NO_PERMISSION, REJECTED_FOREVER, REJECTED_ASK_AGAIN
}