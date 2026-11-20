package com.example.waterme.data

import android.content.Context

/**
 * Simple manual dependency container for the app.
 */
interface AppContainer {
    val waterRepository: WaterRepository
}

class DefaultAppContainer(context: Context) : AppContainer {
    override val waterRepository: WaterRepository = WorkManagerWaterRepository(context)
}
