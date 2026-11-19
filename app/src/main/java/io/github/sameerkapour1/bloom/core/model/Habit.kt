package io.github.sameerkapour1.bloom.core.model

/** Whether a habit is a simple done/not-done tap or a value against a target (DL-6). */
enum class TrackingType { BOOLEAN, MEASURABLE }

/** Pause is also encoded as a `NONE` schedule row, so history stays readable (DL-5). */
enum class HabitStatus { ACTIVE, PAUSED, ARCHIVED }

/**
 * Immutable habit as stored in `habit` (plan.md §8.1).
 *
 * [targetValue] and [unit] are required for [TrackingType.MEASURABLE] and must be
 * null for [TrackingType.BOOLEAN]; validation lives in the habit repository so a
 * UI bug cannot persist an incomplete measurable habit.
 */
data class Habit(
    val id: String,
    val name: String,
    val iconKey: String,
    val colorArgb: Int,
    val trackingType: TrackingType,
    val createdOn: TrackingDate,
    val createdAt: Long,
    val updatedAt: Long,
    val description: String? = null,
    val categoryId: String? = null,
    val targetValue: Double? = null,
    val unit: String? = null,
    val status: HabitStatus = HabitStatus.ACTIVE,
    val sortOrder: Int = 0,
    val archivedAt: Long? = null,
)
