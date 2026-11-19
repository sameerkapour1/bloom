package io.github.sameerkapour1.bloom.core.data.db.entity

import io.github.sameerkapour1.bloom.core.model.Category
import io.github.sameerkapour1.bloom.core.model.Completion
import io.github.sameerkapour1.bloom.core.model.Habit
import io.github.sameerkapour1.bloom.core.model.HabitSchedule
import io.github.sameerkapour1.bloom.core.model.HabitStatus
import io.github.sameerkapour1.bloom.core.model.ScheduleType
import io.github.sameerkapour1.bloom.core.model.TrackingDate
import io.github.sameerkapour1.bloom.core.model.TrackingType

/*
 * Mappings between the immutable model and the stored rows.
 *
 * Only habit, schedule, completion and category are mapped now; goal and reminder
 * mappers arrive with their repositories so no unused code ships ahead of its user.
 */

fun HabitEntity.toModel(): Habit = Habit(
    id = id,
    name = name,
    description = description,
    categoryId = categoryId,
    iconKey = iconKey,
    colorArgb = colorArgb,
    trackingType = TrackingType.valueOf(trackingType),
    targetValue = targetValue,
    unit = unit,
    status = HabitStatus.valueOf(status),
    sortOrder = sortOrder,
    createdOn = TrackingDate.ofEpochDay(createdOn),
    createdAt = createdAt,
    updatedAt = updatedAt,
    archivedAt = archivedAt,
)

fun Habit.toEntity(): HabitEntity = HabitEntity(
    id = id,
    name = name,
    description = description,
    categoryId = categoryId,
    iconKey = iconKey,
    colorArgb = colorArgb,
    trackingType = trackingType.name,
    targetValue = targetValue,
    unit = unit,
    status = status.name,
    sortOrder = sortOrder,
    createdOn = createdOn.epochDay,
    createdAt = createdAt,
    updatedAt = updatedAt,
    archivedAt = archivedAt,
)

fun HabitScheduleEntity.toModel(): HabitSchedule = HabitSchedule(
    id = id,
    habitId = habitId,
    effectiveFrom = TrackingDate.ofEpochDay(effectiveFrom),
    scheduleType = ScheduleType.valueOf(scheduleType),
    weekdaysMask = weekdaysMask,
    timesPerWeek = timesPerWeek,
    createdAt = createdAt,
)

fun HabitSchedule.toEntity(): HabitScheduleEntity = HabitScheduleEntity(
    id = id,
    habitId = habitId,
    effectiveFrom = effectiveFrom.epochDay,
    scheduleType = scheduleType.name,
    weekdaysMask = weekdaysMask,
    timesPerWeek = timesPerWeek,
    createdAt = createdAt,
)

fun CompletionEntity.toModel(): Completion = Completion(
    id = id,
    habitId = habitId,
    trackingDate = TrackingDate.ofEpochDay(trackingDate),
    value = value,
    targetValueSnapshot = targetValueSnapshot,
    completed = completed,
    note = note,
    recordedZoneId = recordedZoneId,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun Completion.toEntity(): CompletionEntity = CompletionEntity(
    id = id,
    habitId = habitId,
    trackingDate = trackingDate.epochDay,
    value = value,
    targetValueSnapshot = targetValueSnapshot,
    completed = completed,
    note = note,
    recordedZoneId = recordedZoneId,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun CategoryEntity.toModel(): Category = Category(
    id = id,
    key = key,
    customName = customName,
    iconKey = iconKey,
    colorArgb = colorArgb,
    sortOrder = sortOrder,
    createdAt = createdAt,
)

fun Category.toEntity(): CategoryEntity = CategoryEntity(
    id = id,
    key = key,
    customName = customName,
    iconKey = iconKey,
    colorArgb = colorArgb,
    sortOrder = sortOrder,
    createdAt = createdAt,
)
