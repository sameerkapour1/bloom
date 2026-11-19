package io.github.sameerkapour1.bloom.core.domain

import io.github.sameerkapour1.bloom.core.model.Completion
import io.github.sameerkapour1.bloom.core.model.Habit
import io.github.sameerkapour1.bloom.core.model.HabitSchedule
import io.github.sameerkapour1.bloom.core.model.ScheduleType
import io.github.sameerkapour1.bloom.core.model.TrackingDate
import io.github.sameerkapour1.bloom.core.model.TrackingType
import io.github.sameerkapour1.bloom.core.model.Weekday
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test

/** DL-9 (streaks, including the grace rule) and DL-8 for weekly habits. */
class StreakTest {

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
        mask: Int = 0,
        timesPerWeek: Int? = null,
    ) = HabitSchedule(
        id = "s-${type.name}",
        habitId = "h1",
        effectiveFrom = monday,
        scheduleType = type,
        createdAt = 0L,
        weekdaysMask = mask,
        timesPerWeek = timesPerWeek,
    )

    private fun completion(date: TrackingDate): Completion = createCompletion(
        id = "c-${date.epochDay}",
        habitId = "h1",
        trackingDate = date,
        value = 1.0,
        targetValueSnapshot = 1.0,
        recordedZoneId = "Europe/Paris",
        now = 0L,
    )

    private fun current(
        completions: List<Completion>,
        schedules: List<HabitSchedule>,
        today: TrackingDate,
    ) = StreakCalculator.current(habit(), schedules, completions, today)

    @Test
    fun `an unfinished today keeps the run intact`() {
        val completions = listOf(completion(monday), completion(monday + 1), completion(monday + 2))
        // Thursday is scheduled and not yet completed: the streak must still read 3.
        assertEquals(3, current(completions, listOf(schedule(ScheduleType.DAILY)), monday + 3))
    }

    @Test
    fun `completing today extends the run`() {
        val completions = listOf(
            completion(monday),
            completion(monday + 1),
            completion(monday + 2),
            completion(monday + 3),
        )
        assertEquals(4, current(completions, listOf(schedule(ScheduleType.DAILY)), monday + 3))
    }

    @Test
    fun `a missed scheduled day breaks the run and is reported by longest`() {
        val completions = listOf(completion(monday), completion(monday + 1), completion(monday + 3))
        val schedules = listOf(schedule(ScheduleType.DAILY))

        assertEquals(1, current(completions, schedules, monday + 3))
        assertEquals(2, StreakCalculator.longest(habit(), schedules, completions, monday + 3))
    }

    @Test
    fun `unscheduled days are skipped without breaking a weekday run`() {
        val schedules = listOf(
            schedule(
                ScheduleType.WEEKDAYS,
                mask = Weekday.mask(Weekday.MONDAY, Weekday.WEDNESDAY, Weekday.FRIDAY),
            ),
        )
        val completions = listOf(completion(monday), completion(monday + 2), completion(monday + 4))

        assertEquals(3, current(completions, schedules, monday + 4))
    }

    @Test
    fun `no history is zero, never one`() {
        assertEquals(0, current(emptyList(), listOf(schedule(ScheduleType.DAILY)), monday + 3))
        assertEquals(0, StreakCalculator.longest(habit(), listOf(schedule(ScheduleType.DAILY)), emptyList(), monday + 3))
    }

    @Test
    fun `a paused habit has no current streak`() {
        val schedules = listOf(schedule(ScheduleType.NONE))
        assertEquals(0, current(listOf(completion(monday)), schedules, monday + 1))
    }

    @Test
    fun `weekly habits count consecutive met weeks`() {
        val schedules = listOf(schedule(ScheduleType.TIMES_PER_WEEK, timesPerWeek = 2))
        val completions = listOf(
            completion(monday),
            completion(monday + 1),
            completion(monday + 7),
            completion(monday + 8),
        )
        assertEquals(2, current(completions, schedules, monday + 9))
    }

    @Test
    fun `an unfinished current week keeps last week's weekly streak`() {
        val schedules = listOf(schedule(ScheduleType.TIMES_PER_WEEK, timesPerWeek = 2))
        val completions = listOf(completion(monday), completion(monday + 1), completion(monday + 7))
        assertEquals(1, current(completions, schedules, monday + 9))
    }
}
