package com.example.marsphotos.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"

interface MarsPhotosApiService {
    @GET("photos")
    suspend fun getPhotos(): List<MarsPhoto>
}

object MarsPhotosApi {
    val service: MarsPhotosApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarsPhotosApiService::class.java)
    }
}
