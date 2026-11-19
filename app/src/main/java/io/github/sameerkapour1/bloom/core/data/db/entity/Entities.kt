package io.github.sameerkapour1.bloom.core.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/*
 * Room tables from plan.md §8.1.
 *
 * Columns are primitives only — enums are stored as their stable names and every
 * user-perceived date as an epoch day. That keeps the schema exactly as approved
 * and avoids depending on Room's handling of Kotlin value classes.
 */

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey val id: String,
    val key: String?,
    val customName: String?,
    val iconKey: String,
    val colorArgb: Int,
    val sortOrder: Int,
    val createdAt: Long,
)

@Entity(
    tableName = "habit",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL,
        ),
    ],
    indices = [Index("categoryId")],
)
data class HabitEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String?,
    val categoryId: String?,
    val iconKey: String,
    val colorArgb: Int,
    val trackingType: String,
    val targetValue: Double?,
    val unit: String?,
    val status: String,
    val sortOrder: Int,
    val createdOn: Long,
    val createdAt: Long,
    val updatedAt: Long,
    val archivedAt: Long?,
)

@Entity(
    tableName = "habit_schedule",
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["id"],
            childColumns = ["habitId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["habitId", "effectiveFrom"], unique = true, name = "idx_schedule_habit_from"),
    ],
)
data class HabitScheduleEntity(
    @PrimaryKey val id: String,
    val habitId: String,
    val effectiveFrom: Long,
    val scheduleType: String,
    val weekdaysMask: Int,
    val timesPerWeek: Int?,
    val createdAt: Long,
)

@Entity(
    tableName = "completion",
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["id"],
            childColumns = ["habitId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["habitId", "trackingDate"], unique = true, name = "idx_completion_habit_date"),
        Index(value = ["trackingDate"], name = "idx_completion_date"),
    ],
)
data class CompletionEntity(
    @PrimaryKey val id: String,
    val habitId: String,
    val trackingDate: Long,
    val value: Double,
    val targetValueSnapshot: Double,
    val completed: Boolean,
    val note: String?,
    val recordedZoneId: String,
    val createdAt: Long,
    val updatedAt: Long,
)

@Entity(tableName = "goal")
data class GoalEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String?,
    val targetDate: Long?,
    val status: String,
    val createdAt: Long,
    val updatedAt: Long,
    val archivedAt: Long?,
)

@Entity(
    tableName = "goal_habit",
    primaryKeys = ["goalId", "habitId"],
    foreignKeys = [
        ForeignKey(
            entity = GoalEntity::class,
            parentColumns = ["id"],
            childColumns = ["goalId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["id"],
            childColumns = ["habitId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("habitId")],
)
data class GoalHabitEntity(
    val goalId: String,
    val habitId: String,
)

@Entity(
    tableName = "goal_milestone",
    foreignKeys = [
        ForeignKey(
            entity = GoalEntity::class,
            parentColumns = ["id"],
            childColumns = ["goalId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("goalId")],
)
data class GoalMilestoneEntity(
    @PrimaryKey val id: String,
    val goalId: String,
    val title: String,
    val sortOrder: Int,
    val completedAt: Long?,
)

@Entity(
    tableName = "reminder",
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["id"],
            childColumns = ["habitId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class ReminderEntity(
    @PrimaryKey val habitId: String,
    val hour: Int,
    val minute: Int,
    val enabled: Boolean,
)
