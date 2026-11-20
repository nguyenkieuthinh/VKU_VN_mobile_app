package com.example.waterme.data

import com.example.waterme.model.Plant
import java.util.concurrent.TimeUnit

/**
 * Entry point for the plant data and the reminder scheduling used by the UI.
 */
interface WaterRepository {
    fun scheduleReminder(duration: Long, unit: TimeUnit, plantName: String)
    val plants: List<Plant>
}
