package io.github.sameerkapour1.bloom.core.domain

import io.github.sameerkapour1.bloom.core.model.TrackingDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/** DL-6 (snapshot, accumulation, undo) and DL-11 (aggregation inputs). */
class MeasurableTest {

    @Test
    fun `partial progress is not complete`() {
        val progress = MeasurableMath.accumulate(existingValue = null, delta = 1.5, targetValueSnapshot = 2.0)

        assertEquals(1.5, progress.value, 0.0001)
        assertEquals(0.75, progress.fraction, 0.0001)
        assertFalse(progress.completed)
    }

    @Test
    fun `reaching the target completes the day`() {
        val progress = MeasurableMath.accumulate(existingValue = 1.5, delta = 0.5, targetValueSnapshot = 2.0)

        assertTrue(progress.completed)
        assertEquals(1.0, progress.fraction, 0.0001)
    }

    @Test
    fun `undo subtracts and never goes below zero`() {
        val undone = MeasurableMath.accumulate(existingValue = 2.0, delta = -0.5, targetValueSnapshot = 2.0)
        assertEquals(1.5, undone.value, 0.0001)
        assertFalse(undone.completed)

        val floored = MeasurableMath.accumulate(existingValue = 1.0, delta = -5.0, targetValueSnapshot = 2.0)
        assertEquals(0.0, floored.value, 0.0001)
    }

    @Test
    fun `a later target change cannot rewrite a stored day`() {
        val stored = createCompletion(
            id = "c1",
            habitId = "h1",
            trackingDate = TrackingDate.ofEpochDay(20_000),
            value = 2.0,
            targetValueSnapshot = 2.0,
            recordedZoneId = "Europe/Paris",
            now = 0L,
        )

        assertTrue(stored.completed)
        assertEquals(2.0, stored.targetValueSnapshot, 0.0001)
        // The habit target is edited to 4.0 afterwards; the stored row keeps 2.0.
        assertFalse(MeasurableMath.progress(stored.value, 4.0).completed)
    }
}
