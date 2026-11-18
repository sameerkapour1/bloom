// Bloom — root build file. All versions live in gradle/libs.versions.toml (plan.md Appendix A).
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}
