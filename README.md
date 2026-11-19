# Bloom — Habit & Life Tracker

A private, offline-first Android habit tracker. Kotlin, Jetpack Compose, Material 3,
Room. English and French. No account, no ads, no analytics — and deliberately **no
`INTERNET` permission**, so nothing can quietly move your data off the device.

Grow at your own pace.

## Status

| Milestone | State |
|---|---|
| M0 Discovery & decisions | ✅ done — see [`plan.md`](plan.md) |
| **M1 Foundation & CI** | ✅ done — [CI run 5](https://github.com/sameerkapour1/bloom/actions/runs/44301414840) builds `bloom-debug` |
| M2 Data layer | next |
| **M3 Domain core** | 🟡 model and the date, schedule, rate, streak, measurable and goal rules are implemented and unit-tested; locale formatting helpers are still open |
| M4 Core habit flows | first genuinely usable app |
| M5 Main experience · M6 Polish · M7 Reminders · M8 Release readiness | planned |

The app currently renders the navigation shell with placeholder screens. The shared
model and the rules behind schedules, streaks and goals exist and are covered by JVM
tests, but no screen reads them yet; `plan.md` §15 states what each milestone delivers.

## Build and install (from a phone)

There is no local compiler in this workflow by design. GitHub Actions builds, and the
APK is installed straight from the Actions run page.

1. Open this repository in a mobile browser → **Actions** → the newest green **Android CI** run.
2. Scroll to **Artifacts** → tap **bloom-debug**.
3. Open **Files → Downloads**, tap the downloaded zip, **Extract**.
4. Tap `app-debug.apk`. When Android objects, allow "install unknown apps" for your
   browser once. If a previous Bloom build with a different signature is installed,
   uninstall it first.
5. Rebuild any time: **Actions → Android CI → Run workflow**.

Every push to `main` and every pull request also runs three gates that fail the build
rather than let a rule rot:

- `core/domain` and `core/model` must not import Android classes (keeps business rules testable).
- No hardcoded user-facing strings in feature code (all copy comes from `strings.xml`).
- The merged manifest's permissions must match `.github/allowed-permissions.txt`, and
  `INTERNET` may never appear.

## Repository layout

```
app/src/main/java/io/github/sameerkapour1/bloom/
├── core/designsystem/   colour, type, shape, spacing, motion tokens + theme
├── core/model/          immutable domain types (habit, schedule, completion, goal)
├── core/domain/         pure Kotlin rules: schedules, rates, streaks, measurable, goals
├── core/common/         date helpers: epoch days, week starts, reminder instants
├── core/ui/             shared composables
├── navigation/          destinations and the NavHost
└── feature/             home, habits, progress, goals, settings
app/src/test/            JVM tests (run on every push, including the contrast gate)
gradle/libs.versions.toml  every dependency version, pinned, no dynamic versions
plan.md                  the approved plan: scope, schema, domain rules, task list
```

## Testing

- **JVM tests** run in CI on every push: `gradle testDebugUnitTest` (Gradle 9.6.0).
  The Gradle wrapper scripts are deliberately not committed — CI pins the version with
  `gradle/actions/setup-gradle`, so use `gradle <task>` rather than `./gradlew`.
  The suite covers the design-system contrast gate and the M3 rules: effective-dated
  schedules, due and listed habits, completion-rate denominators, streak grace,
  measurable target snapshots, goal progress, DST and locale week starts.
- **Instrumented tests** run on an emulator job, not on pull requests. DAO and migration
  coverage is therefore a known gap, tracked as `BLOOM-M2-07` and risk R16 in `plan.md`.

## Privacy

See [`PRIVACY.md`](PRIVACY.md). Short version: your data stays in the app's private
storage on your device, unencrypted, and Bloom has no permission that could send it
anywhere.

## Licence

Apache-2.0 — see [`LICENSE`](LICENSE).
