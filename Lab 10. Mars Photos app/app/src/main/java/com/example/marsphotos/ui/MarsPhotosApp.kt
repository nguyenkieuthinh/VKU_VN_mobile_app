package com.example.marsphotos.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.example.marsphotos.R
import com.example.marsphotos.data.MarsPhoto
import com.example.marsphotos.data.RemoteImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarsPhotosApp(marsViewModel: MarsViewModel = viewModel()) {
    val uiState by marsViewModel.uiState.collectAsState()
    val imageLinksViewModel: ImageLinksViewModel = viewModel()
    var selectedTab by rememberSaveable { mutableStateOf(0) }

    LaunchedEffect(selectedTab) {
        if (selectedTab == 1) imageLinksViewModel.loadImages()
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(title = { Text(stringResource(R.string.app_name)) })
                TabRow(selectedTabIndex = selectedTab) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text(stringResource(R.string.mars_images)) }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text(stringResource(R.string.internet_images)) }
                    )
                }
            }
        }
    ) { contentPadding ->
        val contentModifier = Modifier.padding(contentPadding)
        if (selectedTab == 1) {
            InternetImagesScreen(imageLinksViewModel, contentModifier)
        } else {
            when (val state = uiState) {
                MarsUiState.Loading -> LoadingScreen(contentModifier)
                is MarsUiState.Success -> PhotosGridScreen(
                    photos = state.photos,
                    modifier = contentModifier
                )
                MarsUiState.Error -> ErrorScreen(
                    message = stringResource(R.string.loading_error),
                    retryAction = marsViewModel::getMarsPhotos,
                    modifier = contentModifier
                )
            }
        }
    }
}

@Composable
private fun PhotosGridScreen(photos: List<MarsPhoto>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = photos, key = { photo -> photo.id }) { photo ->
            ImageCard(
                id = photo.id,
                imageUrl = photo.imageUrl,
                description = stringResource(R.string.mars_photo_description, photo.id)
            )
        }
    }
}

@Composable
private fun InternetImagesScreen(
    imageLinksViewModel: ImageLinksViewModel,
    modifier: Modifier = Modifier
) {
    val state by imageLinksViewModel.uiState.collectAsState()

    when (val imageState = state) {
        InternetImagesUiState.Loading -> LoadingScreen(modifier)
        is InternetImagesUiState.Success -> {
            if (imageState.images.isEmpty()) {
                EmptyImagesScreen(modifier, imageLinksViewModel::loadImages)
            } else {
                InternetImagesGrid(imageState.images, modifier)
            }
        }
        InternetImagesUiState.Error -> ErrorScreen(
            message = stringResource(R.string.image_links_error),
            retryAction = imageLinksViewModel::loadImages,
            modifier = modifier
        )
    }
}

@Composable
private fun InternetImagesGrid(images: List<RemoteImage>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = images, key = { image -> image.id }) { image ->
            ImageCard(id = image.id, imageUrl = image.url, description = image.title)
        }
    }
}

@Composable
private fun ImageCard(
    id: String,
    imageUrl: String,
    description: String,
    modifier: Modifier = Modifier
) {
    var imageState by remember(id) {
        mutableStateOf<AsyncImagePainter.State>(AsyncImagePainter.State.Empty)
    }

    Card(
        modifier = modifier.fillMaxWidth().aspectRatio(1.15f),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = description,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                onState = { imageState = it }
            )

            when (imageState) {
                is AsyncImagePainter.State.Loading,
                AsyncImagePainter.State.Empty -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is AsyncImagePainter.State.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.BrokenImage,
                            contentDescription = stringResource(R.string.image_load_failed),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                is AsyncImagePainter.State.Success -> Unit
            }
        }
    }
}

@Composable
private fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyImagesScreen(modifier: Modifier = Modifier, retryAction: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(stringResource(R.string.empty_image_links))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
private fun ErrorScreen(message: String, retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.BrokenImage,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error
        )
        Text(
            text = message,
            modifier = Modifier.padding(vertical = 16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}
