package io.github.sameerkapour1.bloom.core.domain

import io.github.sameerkapour1.bloom.core.model.Completion
import io.github.sameerkapour1.bloom.core.model.Goal
import io.github.sameerkapour1.bloom.core.model.GoalMilestone
import io.github.sameerkapour1.bloom.core.model.Habit
import io.github.sameerkapour1.bloom.core.model.HabitSchedule
import io.github.sameerkapour1.bloom.core.model.ScheduleType
import io.github.sameerkapour1.bloom.core.model.TrackingDate
import io.github.sameerkapour1.bloom.core.model.TrackingType
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/** DL-12: milestones only when nothing is linked, honest aggregate otherwise. */
class GoalProgressTest {

    private val monday = TrackingDate.of(LocalDate.of(2026, 11, 16))

    private fun goal() = Goal(id = "g1", title = "Read more", createdAt = 0L, updatedAt = 0L)

    private fun habit() = Habit(
        id = "h1",
        name = "Read",
        iconKey = "book",
        colorArgb = 0,
        trackingType = TrackingType.BOOLEAN,
        createdOn = monday,
        createdAt = 0L,
        updatedAt = 0L,
    )

    private fun milestone(id: String, done: Boolean) = GoalMilestone(
        id = id,
        goalId = "g1",
        title = id,
        completedAt = if (done) 1L else null,
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

    private fun dailySchedule() = HabitSchedule(
        id = "s1",
        habitId = "h1",
        effectiveFrom = monday,
        scheduleType = ScheduleType.DAILY,
        createdAt = 0L,
    )

    @Test
    fun `a goal without linked habits reports milestones only`() {
        val progress = GoalProgressCalculator.compute(
            goal = goal(),
            milestones = listOf(milestone("a", true), milestone("b", false)),
            linkedHabits = emptyList(),
            schedules = emptyList(),
            completions = emptyList(),
            today = monday + 2,
        )

        assertEquals(1, progress.milestonesDone)
        assertEquals(2, progress.milestonesTotal)
        assertNull(progress.habitProgress)
        assertFalse(progress.hasHabitSignal)
    }

    @Test
    fun `a linked habit contributes its own completion rate`() {
        val progress = GoalProgressCalculator.compute(
            goal = goal(),
            milestones = listOf(milestone("a", true)),
            linkedHabits = listOf(habit()),
            schedules = listOf(dailySchedule()),
            completions = listOf(completion(monday), completion(monday + 1)),
            today = monday + 2,
        )

        assertEquals(1, progress.milestonesDone)
        assertEquals(3, progress.habitProgress!!.scheduled)
        assertEquals(2, progress.habitProgress!!.completed)
        assertTrue(progress.hasHabitSignal)
        assertEquals(2.0 / 3.0, progress.habitRate!!, 0.0001)
    }
}
