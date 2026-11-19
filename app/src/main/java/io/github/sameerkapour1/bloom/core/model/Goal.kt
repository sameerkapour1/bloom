package io.github.sameerkapour1.bloom.core.model

/** Goal lifecycle. Progress is always computed from linked habits and milestones (DL-12). */
enum class GoalStatus { ACTIVE, ACHIEVED, ARCHIVED }

data class Goal(
    val id: String,
    val title: String,
    val createdAt: Long,
    val updatedAt: Long,
    val description: String? = null,
    val targetDate: TrackingDate? = null,
    val status: GoalStatus = GoalStatus.ACTIVE,
    val archivedAt: Long? = null,
)

/** A milestone is manual: nothing may complete it automatically (DL-12). */
data class GoalMilestone(
    val id: String,
    val goalId: String,
    val title: String,
    val sortOrder: Int = 0,
    val completedAt: Long? = null,
) {
    val isCompleted: Boolean get() = completedAt != null
}

/** Link between a goal and a habit. Deleting either side cascades this row (§8.2). */
data class GoalHabitLink(val goalId: String, val habitId: String)
