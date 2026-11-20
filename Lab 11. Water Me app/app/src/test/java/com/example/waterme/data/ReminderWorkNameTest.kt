package com.example.waterme.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class ReminderWorkNameTest {

    @Test
    fun samePlantAndDuration_producesSameWorkName() {
        assertEquals(reminderWorkName("Peony", 5), reminderWorkName("Peony", 5))
    }

    @Test
    fun differentDurations_produceDifferentWorkNames() {
        assertNotEquals(reminderWorkName("Peony", 5), reminderWorkName("Peony", 1))
    }

    @Test
    fun differentPlants_produceDifferentWorkNames() {
        assertNotEquals(reminderWorkName("Peony", 1), reminderWorkName("Pothos", 1))
    }
}
