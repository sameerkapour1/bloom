# Contributing to Bloom

Thanks for looking. Bloom is a small, deliberately simple Android app; the fastest way to
be useful is a focused pull request against one item in `plan.md` §16.

## Before you start

- Read `plan.md` §4 (scope), §9 (date and streak rules) and §16 (task list). Changes that
  are not on that list should start as an issue so they can be added to it deliberately.
- **Do not weaken the gates.** The Android-free domain layer, the no-hardcoded-strings
  rule and the permission allowlist exist because each one protects a promise the app
  makes to its users. A pull request that loosens one needs to argue for it in the issue.

## Practical rules

- Every user-facing string goes in `res/values/strings.xml` **and** `res/values-fr/strings.xml`.
- Business logic belongs in `core/domain` with no Android imports, so it can be unit tested
  on the JVM. Dates are civil dates (`epochDay`), never instants.
- No new dependency without a reason, a licence check and a pinned version in
  `gradle/libs.versions.toml`.
- Never commit secrets, keystores or `local.properties`.
- Run `gradle testDebugUnitTest lintDebug assembleDebug` before opening a pull request.

## Reporting bugs

Open an issue with what you did, what you expected, what happened, and your Android version
and device if it looks device-specific. Screenshots help.

## Licence

By contributing you agree your work is licensed under Apache-2.0 (see `LICENSE`).
