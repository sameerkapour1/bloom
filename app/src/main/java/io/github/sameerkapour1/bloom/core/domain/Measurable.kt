package io.github.sameerkapour1.bloom.core.domain

import io.github.sameerkapour1.bloom.core.model.Completion
import io.github.sameerkapour1.bloom.core.model.TrackingDate

/**
 * Progress of one measurable day against that day's stored target snapshot.
 *
 * [completed] is always `value >= target`, never a cached flag supplied by a
 * caller. That is why a later habit-target change cannot rewrite history (DL-6).
 */
data class MeasurableProgress(val value: Double, val target: Double) {
    val completed: Boolean get() = value >= target

    /** Clamped 0..1 fraction for the progress ring; never divides by zero. */
    val fraction: Double
        get() = when {
            target <= 0.0 -> if (value > 0.0) 1.0 else 0.0
            else -> (value / target).coerceIn(0.0, 1.0)
        }
}

/** Pure helpers behind the measurable completion control (DL-6, DL-11). */
object MeasurableMath {

    fun progress(value: Double, targetValueSnapshot: Double): MeasurableProgress =
        MeasurableProgress(value, targetValueSnapshot)

    /**
     * Adds [delta] to an existing value. A negative delta is the in-session undo and
     * never takes the value below zero. The target stays the day's snapshot.
     */
    fun accumulate(
        existingValue: Double?,
        delta: Double,
        targetValueSnapshot: Double,
    ): MeasurableProgress {
        val next = (existingValue ?: 0.0) + delta
        return MeasurableProgress(next.coerceAtLeast(0.0), targetValueSnapshot)
    }
}

/**
 * Builds a completion row with its target snapshot frozen at write time.
 *
 * Boolean habits pass `value = 1.0` and `targetValueSnapshot = 1.0`; measurable
 * habits pass their accumulated value and the habit's target *as it is now*, which
 * is the snapshot future edits will not touch.
 */
fun createCompletion(
    id: String,
    habitId: String,
    trackingDate: TrackingDate,
    value: Double,
    targetValueSnapshot: Double,
    recordedZoneId: String,
    now: Long,
    note: String? = null,
): Completion {
    val progress = MeasurableMath.progress(value, targetValueSnapshot)
    return Completion(
        id = id,
        habitId = habitId,
        trackingDate = trackingDate,
        targetValueSnapshot = targetValueSnapshot,
        completed = progress.completed,
        recordedZoneId = recordedZoneId,
        createdAt = now,
        updatedAt = now,
        value = value,
        note = note,
    )
}
