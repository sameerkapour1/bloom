package io.github.sameerkapour1.bloom.core.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import io.github.sameerkapour1.bloom.core.data.db.entity.HabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Query("SELECT * FROM habit ORDER BY sortOrder ASC, createdAt ASC")
    fun observeAll(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habit WHERE status = :status ORDER BY sortOrder ASC, createdAt ASC")
    fun observeWithStatus(status: String): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habit WHERE id = :id")
    fun observeById(id: String): Flow<HabitEntity?>

    @Query("SELECT * FROM habit WHERE id = :id")
    suspend fun findById(id: String): HabitEntity?

    @Upsert
    suspend fun upsert(habit: HabitEntity)

    @Query("DELETE FROM habit WHERE id = :id")
    suspend fun deleteById(id: String)
}
