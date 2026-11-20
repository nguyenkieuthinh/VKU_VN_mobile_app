package com.example.waterme.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.waterme.R

/**
 * Background worker that posts the "It's time to water…" notification.
 *
 * It receives the plant name through [inputData] and runs once after the delay
 * configured by the [com.example.waterme.data.WorkManagerWaterRepository].
 */
class WaterReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val plantName = inputData.getString(nameKey) ?: return Result.failure()

        makePlantReminderNotification(
            applicationContext.resources.getString(R.string.time_to_water, plantName),
            applicationContext
        )

        return Result.success()
    }

    companion object {
        const val nameKey = "NAME"
    }
}
