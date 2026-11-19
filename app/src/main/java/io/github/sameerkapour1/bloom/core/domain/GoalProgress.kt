package io.github.sameerkapour1.bloom.core.domain

import io.github.sameerkapour1.bloom.core.model.Completion
import io.github.sameerkapour1.bloom.core.model.Goal
import io.github.sameerkapour1.bloom.core.model.GoalMilestone
import io.github.sameerkapour1.bloom.core.model.Habit
import io.github.sameerkapour1.bloom.core.model.HabitSchedule
import io.github.sameerkapour1.bloom.core.model.TrackingDate
import io.github.sameerkapour1.bloom.core.model.Weekday

/**
 * Progress of a goal.
 *
 * [habitProgress] is null when the goal has no linked habits; the UI then shows
 * milestones only, never an invented percentage. Milestones are manual, so
 * [milestonesDone] can only change when the user ticks one (DL-12).
 */
data class GoalProgress(
    val milestonesDone: Int,
    val milestonesTotal: Int,
    val habitProgress: RateResult?,
) {
    val hasHabitSignal: Boolean get() = habitProgress?.scheduled?.let { it > 0 } == true
    val habitRate: Double? get() = habitProgress?.rate
}

object GoalProgressCalculator {

    /**
     * Aggregates the completion rates of the goal's linked habits over each habit's
     * own lifetime up to [today].
     *
     * The window starts at each habit's creation rather than at the goal's creation:
     * the stored goal has no civil creation date, only an audit timestamp, and
     * converting that to a date would depend on the current device zone. Counting
     * only days on which a habit was actually scheduled keeps the promise that the
     * number means "the habits you linked", not proof the goal is achieved.
     */
    fun compute(
        goal: Goal,
        milestones: List<GoalMilestone>,
        linkedHabits: List<Habit>,
        schedules: List<HabitSchedule>,
        completions: List<Completion>,
        today: TrackingDate,
        firstDay: Weekday = Weekday.MONDAY,
    ): GoalProgress {
        val ownMilestones = milestones.filter { it.goalId == goal.id }
        val perHabit = linkedHabits.map { habit ->
            CompletionRateCalculator.forHabit(
                habit = habit,
                schedules = schedules,
                completions = completions,
                from = habit.createdOn,
                to = today,
                firstDay = firstDay,
            )
        }
        return GoalProgress(
            milestonesDone = ownMilestones.count { it.isCompleted },
            milestonesTotal = ownMilestones.size,
            habitProgress = if (perHabit.isEmpty()) null else CompletionRateCalculator.aggregate(perHabit),
        )
    }
}
