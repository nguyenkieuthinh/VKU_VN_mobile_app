package com.example.marsphotos.data

private const val MAX_INTERNET_IMAGES = 20

class ImageLinksRepository(
    private val service: ImageLinksApiService = ImageLinksApi.service
) {
    suspend fun getImages(): List<RemoteImage> {
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
