// Bloom — root build file. All versions live in gradle/libs.versions.toml (plan.md Appendix A).
plugins {
    alias(libs.plugins.android.application) apply false
    // No kotlin-android: AGP 9 provides Kotlin support itself.
    alias(libs.plugins.kotlin.compose) apply false
}
