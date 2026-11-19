package io.github.sameerkapour1.bloom.core.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.sameerkapour1.bloom.core.data.db.dao.CategoryDao
import io.github.sameerkapour1.bloom.core.data.db.dao.CompletionDao
import io.github.sameerkapour1.bloom.core.data.db.dao.HabitDao
import io.github.sameerkapour1.bloom.core.data.db.dao.HabitScheduleDao
import io.github.sameerkapour1.bloom.core.data.db.entity.CategoryEntity
import io.github.sameerkapour1.bloom.core.data.db.entity.CompletionEntity
import io.github.sameerkapour1.bloom.core.data.db.entity.GoalEntity
import io.github.sameerkapour1.bloom.core.data.db.entity.GoalHabitEntity
import io.github.sameerkapour1.bloom.core.data.db.entity.GoalMilestoneEntity
import io.github.sameerkapour1.bloom.core.data.db.entity.HabitEntity
import io.github.sameerkapour1.bloom.core.data.db.entity.HabitScheduleEntity
import io.github.sameerkapour1.bloom.core.data.db.entity.ReminderEntity

/**
 * The v1 schema from plan.md §8.1.
 *
 * `exportSchema` is on so `app/schemas/` can feed real migration tests when v2
 * exists (§8.2); `fallbackToDestructiveMigration` must never be added.
 */
@Database(
    entities = [
        CategoryEntity::class,
        HabitEntity::class,
        HabitScheduleEntity::class,
        CompletionEntity::class,
        GoalEntity::class,
        GoalHabitEntity::class,
        GoalMilestoneEntity::class,
        ReminderEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class BloomDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao

    abstract fun habitScheduleDao(): HabitScheduleDao

    abstract fun completionDao(): CompletionDao

    abstract fun categoryDao(): CategoryDao

    companion object {
        const val FILE_NAME = "bloom.db"

        fun create(context: Context): BloomDatabase =
            Room.databaseBuilder(context.applicationContext, BloomDatabase::class.java, FILE_NAME)
                .build()
    }
}
