package com.ingenious.familyguard.ui.screens.whitelist

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ingenious.familyguard.domain.model.AllowedApp
import com.ingenious.familyguard.domain.repository.WhitelistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WhitelistViewModel @Inject constructor(
    private val whitelistRepository: WhitelistRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val allowedApps: StateFlow<List<AllowedApp>> = whitelistRepository.getAllowedApps()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _installedApps = MutableStateFlow<List<AppInfo>>(emptyList())
    val installedApps: StateFlow<List<AppInfo>> = _installedApps.asStateFlow()

    init {
        loadInstalledApps()
    }

    private fun loadInstalledApps() {
        viewModelScope.launch {
            val pm = context.packageManager
            val apps = pm.getInstalledApplications(PackageManager.GET_META_DATA)
                .filter { (it.flags and ApplicationInfo.FLAG_SYSTEM) == 0 }
                .map { AppInfo(it.packageName, it.loadLabel(pm).toString()) }
                .sortedBy { it.name }
            _installedApps.value = apps
        }
    }

    fun addApp(packageName: String, name: String) {
        viewModelScope.launch {
            whitelistRepository.addAllowedApp(AllowedApp(packageName, name))
        }
    }

    fun removeApp(packageName: String) {
        viewModelScope.launch {
            whitelistRepository.removeAllowedApp(packageName)
        }
    }
}

data class AppInfo(val packageName: String, val name: String)
