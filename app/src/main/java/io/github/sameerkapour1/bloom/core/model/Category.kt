package io.github.sameerkapour1.bloom.core.model

/**
 * A habit category: either a built-in identified by [key] (its label is resolved
 * from `strings.xml`, never stored) or a user-defined one named by [customName].
 * Exactly one of the two is non-null (plan.md §8.1).
 */
data class Category(
    val id: String,
    val iconKey: String,
    val colorArgb: Int,
    val createdAt: Long,
    val key: String? = null,
    val customName: String? = null,
    val sortOrder: Int = 0,
) {
    val isBuiltIn: Boolean get() = key != null
}
