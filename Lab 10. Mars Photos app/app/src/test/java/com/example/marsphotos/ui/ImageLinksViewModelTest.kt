package com.example.marsphotos.ui

import com.example.marsphotos.data.ImageLinksDataSource
import com.example.marsphotos.data.RemoteImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ImageLinksViewModelTest {
    @Test
    fun loadImages_whenLinksAreAvailable_emitsSuccess() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        try {
            val images = listOf(RemoteImage("https://example.com/lake.jpg"))
            val viewModel = ImageLinksViewModel(ImageLinksDataSource { images })

            viewModel.loadImages()
            advanceUntilIdle()

            assertEquals(InternetImagesUiState.Success(images), viewModel.uiState.value)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun loadImages_whenLinkFileFails_emitsError() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        try {
            val viewModel = ImageLinksViewModel(
                ImageLinksDataSource { throw IllegalStateException("network failure") }
            )

            viewModel.loadImages()
            advanceUntilIdle()

            assertEquals(InternetImagesUiState.Error, viewModel.uiState.value)
        } finally {
            Dispatchers.resetMain()
        }
    }
}
