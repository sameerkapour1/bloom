package io.github.sameerkapour1.bloom.core.model

/**
 * The single completion row for one habit and one tracking date (plan.md §8.1).
 *
 * [targetValueSnapshot] is copied from the habit when the row is first written and
 * never changes afterwards, so editing a habit's target applies to future days
 * only (DL-6). [completed] is therefore a pure function of [value] and
 * [targetValueSnapshot] and cannot go stale.
 *
 * [recordedZoneId] is audit information only; it is never used for grouping.
 */
data class Completion(
    val id: String,
    val habitId: String,
    val trackingDate: TrackingDate,
    val targetValueSnapshot: Double,
    val completed: Boolean,
    val recordedZoneId: String,
    val createdAt: Long,
    val updatedAt: Long,
    val value: Double = 0.0,
    val note: String? = null,
)
