package com.ingenious.familyguard.ui.screens.dashboard

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ingenious.familyguard.domain.repository.SettingsRepository
import com.ingenious.familyguard.util.PermissionUtils
import com.ingenious.familyguard.util.ServiceUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val isProtectionEnabled: StateFlow<Boolean> = settingsRepository.isProtectionEnabled()
        .onEach { enabled ->
            if (enabled) ServiceUtils.startMonitoringService(context)
            else ServiceUtils.stopMonitoringService(context)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _permissionsGranted = MutableStateFlow(false)
    val permissionsGranted: StateFlow<Boolean> = _permissionsGranted.asStateFlow()

    init {
        checkPermissions()
    }

    fun checkPermissions() {
        val accessibility = PermissionUtils.isAccessibilityServiceEnabled(context)
        val admin = PermissionUtils.isDeviceAdminActive(context)
        _permissionsGranted.value = accessibility && admin
    }

    fun toggleProtection(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setProtectionEnabled(enabled)
        }
    }
}
