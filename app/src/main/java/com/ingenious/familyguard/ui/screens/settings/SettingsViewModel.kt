package com.ingenious.familyguard.ui.screens.settings

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
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val isProtectionEnabled: StateFlow<Boolean> = settingsRepository.isProtectionEnabled()
        .onEach { enabled ->
            if (enabled) ServiceUtils.startMonitoringService(context)
            else ServiceUtils.stopMonitoringService(context)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _isAccessibilityEnabled = MutableStateFlow(false)
    val isAccessibilityEnabled: StateFlow<Boolean> = _isAccessibilityEnabled.asStateFlow()

    private val _isDeviceAdminActive = MutableStateFlow(false)
    val isDeviceAdminActive: StateFlow<Boolean> = _isDeviceAdminActive.asStateFlow()

    init {
        checkPermissions()
    }

    fun checkPermissions() {
        _isAccessibilityEnabled.value = PermissionUtils.isAccessibilityServiceEnabled(context)
        _isDeviceAdminActive.value = PermissionUtils.isDeviceAdminActive(context)
    }

    fun setProtectionEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setProtectionEnabled(enabled)
        }
    }
}
