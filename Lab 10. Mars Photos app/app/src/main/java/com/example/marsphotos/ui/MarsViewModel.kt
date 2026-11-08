package com.example.marsphotos.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.data.MarsPhoto
import com.example.marsphotos.data.MarsPhotosApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val MAX_MARS_PHOTOS = 20

sealed interface MarsUiState {
    data object Loading : MarsUiState
    data class Success(val photos: List<MarsPhoto>) : MarsUiState
    data object Error : MarsUiState
}

class MarsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<MarsUiState>(MarsUiState.Loading)
    val uiState: StateFlow<MarsUiState> = _uiState.asStateFlow()

    init {
        getMarsPhotos()
    }

    fun getMarsPhotos() {
        viewModelScope.launch {
            _uiState.value = MarsUiState.Loading
            _uiState.value = try {
                MarsUiState.Success(MarsPhotosApi.service.getPhotos().take(MAX_MARS_PHOTOS))
            } catch (_: Exception) {
                MarsUiState.Error
            }
        }
    }
}
