package com.example.marsphotos.data

import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val RAW_GITHUB_URL = "https://raw.githubusercontent.com/"
private const val IMAGE_LINKS_PATH =
    "nguyenkieuthinh/VKU_VN_mobile_app/main/Lab%2010.%20Mars%20Photos%20app/img/image-links.txt"

interface ImageLinksApiService {
    @Headers("Cache-Control: no-cache")
    @GET(IMAGE_LINKS_PATH)
    suspend fun getImageLinks(@Query("_") cacheBuster: Long): ResponseBody
}

object ImageLinksApi {
    val service: ImageLinksApiService by lazy {
        Retrofit.Builder()
            .baseUrl(RAW_GITHUB_URL)
            .build()
            .create(ImageLinksApiService::class.java)
    }
}
