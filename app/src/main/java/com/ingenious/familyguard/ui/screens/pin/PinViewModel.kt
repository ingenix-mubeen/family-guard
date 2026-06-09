package com.ingenious.familyguard.ui.screens.pin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ingenious.familyguard.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PinUiState>(PinUiState.Idle)
    val uiState: StateFlow<PinUiState> = _uiState.asStateFlow()

    fun setupPin(pin: String) {
        viewModelScope.launch {
            settingsRepository.setPin(pin)
            _uiState.value = PinUiState.Success
        }
    }

    fun verifyPin(pin: String) {
        viewModelScope.launch {
            val isValid = settingsRepository.verifyPin(pin)
            if (isValid) {
                _uiState.value = PinUiState.Success
            } else {
                _uiState.value = PinUiState.Error("Invalid PIN")
            }
        }
    }
    
    fun resetState() {
        _uiState.value = PinUiState.Idle
    }
}

sealed interface PinUiState {
    data object Idle : PinUiState
    data object Success : PinUiState
    data class Error(val message: String) : PinUiState
}
