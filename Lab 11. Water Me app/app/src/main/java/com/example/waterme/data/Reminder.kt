package com.example.waterme.data

import androidx.annotation.StringRes
import java.util.concurrent.TimeUnit

/**
 * One reminder option shown in the "Remind me to water…" dialog.
 */
data class Reminder(
    @StringRes val durationRes: Int,
    val duration: Long,
    val unit: TimeUnit,
    val plantName: String
)
