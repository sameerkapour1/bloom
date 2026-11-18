package io.github.sameerkapour1.bloom

import android.app.Application

/**
 * Process-wide entry point.
 *
 * The hand-written `AppContainer` (plan.md decision D2) is created here in M2,
 * when repositories and the database exist. M1 intentionally has no wiring yet.
 */
class BloomApplication : Application()
