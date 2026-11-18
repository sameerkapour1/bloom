# Changelog

All notable changes to Bloom are recorded here. The format follows
[Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and the project uses
[Semantic Versioning](https://semver.org/spec/v2.0.0.html) once it has a release.

## [Unreleased]

### Added — M1 Foundation

- Gradle project (AGP 9.5.0, Gradle 9.6.0, Kotlin 2.4.21, compileSdk 37 / targetSdk 36 /
  minSdk 26) with every version pinned in `gradle/libs.versions.toml`.
- Design system tokens: brand-derived light and dark colour schemes, type scale, shape
  scale, spacing and motion constants, plus an opt-in dynamic-colour path.
- A JVM contrast gate that fails the build if any text/background pair drops below
  WCAG AA (4.5:1).
- Navigation shell: four top-level destinations plus a Settings entry, English and French
  string resources for every label.
- GitHub Actions CI building a debug APK on every push and PR, with three gates
  (Android-free domain layer, no hardcoded UI strings, merged-manifest permission
  allowlist that forbids `INTERNET`).
- `README.md`, `PRIVACY.md`, `LICENSE` (Apache-2.0), contribution and security policy.
