package com.example.waterme.data

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.waterme.model.Plant
import com.example.waterme.worker.WaterReminderWorker
import java.util.concurrent.TimeUnit

/**
 * Unique work name built from the plant name and the reminder duration.
 *
 * Including the duration lets the same plant have several reminders at once,
 * for example "5 seconds" and "1 day".
 */
internal fun reminderWorkName(plantName: String, duration: Long): String =
    "$plantName-$duration"

/**
 * Schedules watering reminders with [WorkManager] and exposes the plant list.
 */
class WorkManagerWaterRepository(context: Context) : WaterRepository {

    private val workManager = WorkManager.getInstance(context)

    override val plants: List<Plant>
        get() = DataSource.plants

    override fun scheduleReminder(duration: Long, unit: TimeUnit, plantName: String) {
        val inputData = Data.Builder()
            .putString(WaterReminderWorker.nameKey, plantName)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<WaterReminderWorker>()
            .setInitialDelay(duration, unit)
            .setInputData(inputData)
            .build()

        workManager.enqueueUniqueWork(
            reminderWorkName(plantName, duration),
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }
}
