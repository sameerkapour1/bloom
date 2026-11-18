# Bloom — Habit & Life Tracker

A private, offline-first Android habit tracker. Kotlin, Jetpack Compose, Material 3,
Room. English and French. No account, no ads, no analytics — and deliberately **no
`INTERNET` permission**, so nothing can quietly move your data off the device.

Grow at your own pace.

## Status

| Milestone | State |
|---|---|
| M0 Discovery & decisions | ✅ done — see [`plan.md`](plan.md) |
| **M1 Foundation & CI** | **in progress — this commit** |
| M2 Data layer · M3 Domain core | next (in parallel) |
| M4 Core habit flows | first genuinely usable app |
| M5 Main experience · M6 Polish · M7 Reminders · M8 Release readiness | planned |

The app currently renders the navigation shell with placeholder screens. It is not
yet a habit tracker; `plan.md` §15 states what each milestone delivers.

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

- `core/domain` must not import Android classes (keeps business rules testable).
- No hardcoded user-facing strings in feature code (all copy comes from `strings.xml`).
- The merged manifest's permissions must match `.github/allowed-permissions.txt`, and
  `INTERNET` may never appear.

## Repository layout

```
app/src/main/java/io/github/sameerkapour1/bloom/
├── core/designsystem/   colour, type, shape, spacing, motion tokens + theme
├── core/ui/             shared composables
├── navigation/          destinations and the NavHost
└── feature/             home, habits, progress, goals, settings
app/src/test/            JVM tests (run on every push, including the contrast gate)
gradle/libs.versions.toml  every dependency version, pinned, no dynamic versions
plan.md                  the approved plan: scope, schema, domain rules, task list
```

## Testing

- **JVM tests** (`gradle testDebugUnitTest`) run in CI on every push. They currently
  contain the design-system contrast gate and, from M3, the date and streak rules.
- **Instrumented tests** run on an emulator job, not on pull requests. DAO and migration
  coverage is therefore a known gap, tracked as `BLOOM-M2-07` and risk R16 in `plan.md`.

## Privacy

See [`PRIVACY.md`](PRIVACY.md). Short version: your data stays in the app's private
storage on your device, unencrypted, and Bloom has no permission that could send it
anywhere.

## Licence

Apache-2.0 — see [`LICENSE`](LICENSE).
