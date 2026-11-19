package io.github.sameerkapour1.bloom.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.sameerkapour1.bloom.core.data.db.entity.CompletionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompletionDao {

    @Query("SELECT * FROM completion WHERE habitId = :habitId ORDER BY trackingDate ASC")
    fun observeForHabit(habitId: String): Flow<List<CompletionEntity>>

    @Query("SELECT * FROM completion WHERE trackingDate BETWEEN :from AND :to ORDER BY trackingDate ASC")
    fun observeRange(from: Long, to: Long): Flow<List<CompletionEntity>>

    @Query("SELECT * FROM completion WHERE trackingDate BETWEEN :from AND :to")
    suspend fun findRange(from: Long, to: Long): List<CompletionEntity>

    @Query("SELECT * FROM completion WHERE habitId = :habitId AND trackingDate = :date LIMIT 1")
    suspend fun find(habitId: String, date: Long): CompletionEntity?

    // One row per habit per day, enforced by idx_completion_habit_date (DL-6).
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(completion: CompletionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(completions: List<CompletionEntity>)

    @Query("DELETE FROM completion WHERE habitId = :habitId AND trackingDate = :date")
    suspend fun delete(habitId: String, date: Long)
}
