package io.github.sameerkapour1.bloom

import android.app.Application
import io.github.sameerkapour1.bloom.core.data.AppContainer

/**
 * Process-wide entry point.
 *
 * Holds the single hand-written [AppContainer] (plan.md decision D2), which owns
 * the Room database. Use it as `(application as BloomApplication).container`.
 */
class BloomApplication : Application() {

    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
