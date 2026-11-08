package com.example.marsphotos.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.data.ImageLinksDataSource
import com.example.marsphotos.data.ImageLinksRepository
import com.example.marsphotos.data.RemoteImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface InternetImagesUiState {
    data object Loading : InternetImagesUiState
    data class Success(val images: List<RemoteImage>) : InternetImagesUiState
    data object Error : InternetImagesUiState
}

class ImageLinksViewModel(
    private val repository: ImageLinksDataSource = ImageLinksRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow<InternetImagesUiState>(InternetImagesUiState.Loading)
    val uiState: StateFlow<InternetImagesUiState> = _uiState.asStateFlow()

    fun loadImages() {
        viewModelScope.launch {
            _uiState.value = InternetImagesUiState.Loading
            _uiState.value = try {
                InternetImagesUiState.Success(repository.getImages())
            } catch (_: Exception) {
                InternetImagesUiState.Error
            }
        }
    }
}
