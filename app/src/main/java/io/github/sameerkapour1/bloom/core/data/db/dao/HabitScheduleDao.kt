package io.github.sameerkapour1.bloom.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.sameerkapour1.bloom.core.data.db.entity.HabitScheduleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitScheduleDao {

    @Query("SELECT * FROM habit_schedule WHERE habitId = :habitId ORDER BY effectiveFrom ASC")
    fun observeForHabit(habitId: String): Flow<List<HabitScheduleEntity>>

    @Query("SELECT * FROM habit_schedule WHERE habitId = :habitId ORDER BY effectiveFrom ASC")
    suspend fun findForHabit(habitId: String): List<HabitScheduleEntity>

    @Query("SELECT * FROM habit_schedule ORDER BY habitId ASC, effectiveFrom ASC")
    suspend fun findAll(): List<HabitScheduleEntity>

    // REPLACE rather than Upsert: the conflict target is the unique (habitId,
    // effectiveFrom) index, so a second edit on one dock day replaces that day's
    // row instead of colliding (DL-3, DL-5).
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(schedule: HabitScheduleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(schedules: List<HabitScheduleEntity>)

    @Query("DELETE FROM habit_schedule WHERE id = :id")
    suspend fun deleteById(id: String)
}
