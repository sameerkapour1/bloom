package io.github.sameerkapour1.bloom.core.data

import android.content.Context
import io.github.sameerkapour1.bloom.core.data.db.BloomDatabase
import io.github.sameerkapour1.bloom.core.data.db.dao.CategoryDao
import io.github.sameerkapour1.bloom.core.data.db.dao.CompletionDao
import io.github.sameerkapour1.bloom.core.data.db.dao.HabitDao
import io.github.sameerkapour1.bloom.core.data.db.dao.HabitScheduleDao

/**
 * Hand-written dependency container (plan.md decision D2). No Hilt, no Dagger:
 * there is one application scope and a handful of dependencies.
 *
 * Repositories and preferences join this container as their tasks land; screens
 * will only ever receive what they need from their ViewModel factory.
 */
class AppContainer(context: Context) {

    val database: BloomDatabase = BloomDatabase.create(context)

    val habitDao: HabitDao get() = database.habitDao()

    val habitScheduleDao: HabitScheduleDao get() = database.habitScheduleDao()

    val completionDao: CompletionDao get() = database.completionDao()

    val categoryDao: CategoryDao get() = database.categoryDao()
}
