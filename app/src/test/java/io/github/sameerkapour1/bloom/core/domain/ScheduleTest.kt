package io.github.sameerkapour1.bloom.core.domain

import io.github.sameerkapour1.bloom.core.model.Habit
import io.github.sameerkapour1.bloom.core.model.HabitSchedule
import io.github.sameerkapour1.bloom.core.model.ScheduleType
import io.github.sameerkapour1.bloom.core.model.TrackingDate
import io.github.sameerkapour1.bloom.core.model.TrackingType
import io.github.sameerkapour1.bloom.core.model.Weekday
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/** DL-3 (effective-dated resolution) and DL-4 (due / listed). */
class ScheduleTest {

    private val monday = TrackingDate.of(LocalDate.of(2026, 11, 16))

    private fun habit(createdOn: TrackingDate = monday) = Habit(
        id = "h1",
        name = "Walk",
        iconKey = "walk",
        colorArgb = 0,
        trackingType = TrackingType.BOOLEAN,
        createdOn = createdOn,
        createdAt = 0L,
        updatedAt = 0L,
    )

    private fun schedule(
        type: ScheduleType,
        effectiveFrom: TrackingDate = monday,
        mask: Int = 0,
        timesPerWeek: Int? = null,
    ) = HabitSchedule(
        id = "s-${type.name}-${effectiveFrom.epochDay}",
        habitId = "h1",
        effectiveFrom = effectiveFrom,
        scheduleType = type,
        createdAt = 0L,
        weekdaysMask = mask,
        timesPerWeek = timesPerWeek,
    )

    @Test
    fun `the schedule with the greatest effective date wins`() {
        val schedules = listOf(
            schedule(ScheduleType.DAILY),
            schedule(ScheduleType.NONE, effectiveFrom = monday + 2),
        )
        assertEquals(
            ScheduleType.DAILY,
            ScheduleResolver.resolve(schedules, monday + 1)?.scheduleType,
        )
        assertEquals(
            ScheduleType.NONE,
            ScheduleResolver.resolve(schedules, monday + 2)?.scheduleType,
        )
    }

    @Test
    fun `weekday schedule is due only on its selected days`() {
        val mask = Weekday.mask(Weekday.MONDAY, Weekday.WEDNESDAY)
        val schedules = listOf(schedule(ScheduleType.WEEKDAYS, mask = mask))
        val habit = habit()

        assertTrue(habit.isDueOn(monday, schedules))
        assertFalse(habit.isDueOn(monday + 1, schedules))
        assertTrue(habit.isDueOn(monday + 2, schedules))
    }

    @Test
    fun `a weekly habit stays listed after its target is met but is no longer due`() {
        val schedules = listOf(schedule(ScheduleType.TIMES_PER_WEEK, timesPerWeek = 3))
        val habit = habit()

        assertTrue(habit.isListedOn(monday + 3, schedules))
        assertTrue(habit.isDueOn(monday, schedules, completedThisWeek = 2))
        assertFalse(habit.isDueOn(monday, schedules, completedThisWeek = 3))
    }

    @Test
    fun `paused and unstarted habits are never due`() {
        assertFalse(habit().isDueOn(monday, listOf(schedule(ScheduleType.NONE))))

        val startedLater = habit(createdOn = monday + 5)
        val daily = listOf(schedule(ScheduleType.DAILY))
        assertFalse(startedLater.isDueOn(monday, daily))
        assertTrue(startedLater.isDueOn(monday + 5, daily))
    }

    @Test
    fun `a habit with no schedule version is not due`() {
        assertFalse(habit().isDueOn(monday, emptyList()))
    }
}
