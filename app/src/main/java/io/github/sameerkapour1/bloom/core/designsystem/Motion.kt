package io.github.sameerkapour1.bloom.core.designsystem

/**
 * Motion constants from plan.md §7. Every animation uses one of these durations
 * so state changes feel consistent; reduced-motion handling is wired in M6.
 */
object BloomMotion {
    /** 100 ms — colour and opacity changes. */
    const val ShortMs = 100

    /** 200 ms — the completion toggle and small transitions. */
    const val MediumMs = 200

    /** 300 ms — screen-level transitions. */
    const val LongMs = 300
}
