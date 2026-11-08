package com.example.marsphotos.data

private const val MAX_INTERNET_IMAGES = 20

fun interface ImageLinksDataSource {
    suspend fun getImages(): List<RemoteImage>
}

class ImageLinksRepository(
    private val service: ImageLinksApiService = ImageLinksApi.service
) : ImageLinksDataSource {
    override suspend fun getImages(): List<RemoteImage> {
        val linksText = service.getImageLinks(System.currentTimeMillis()).use { it.string() }

        return linksText.lineSequence()
            .map(String::trim)
            .filter { it.startsWith("https://") }
            .distinct()
            .take(MAX_INTERNET_IMAGES)
            .map(::RemoteImage)
            .toList()
    }
}
