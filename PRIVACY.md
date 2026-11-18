# Bloom privacy statement (Release 1)

This describes what the app in this repository does today. It is written to be true of
the code, not aspirational — `plan.md` §11 is the engineering counterpart.

## What Bloom stores

Habit names, descriptions, categories, schedules, completion history, goals and
milestones, and your settings. They are stored in the app's private storage on your
device (a local SQLite database and a preferences file).

## What Bloom does not do

- **No account.** Nothing to sign up for, no email, no profile.
- **No network access.** Bloom declares no `INTERNET` permission, so the app cannot
  send data anywhere even if a library wanted to. There is no analytics, no advertising,
  no crash reporting and no remote configuration.
- **No collection.** No name, email, location, contacts, advertising ID or device
  identifier is requested or stored.
- **No third-party sharing.** There is no third party to share with.

## Permissions

Bloom declares one permission of its own: **notifications**, used only for the habit
reminders you switch on. The scheduling library that will provide reminders (`androidx.work`)
adds four more permissions to the installed package: `WAKE_LOCK`, `ACCESS_NETWORK_STATE`,
`RECEIVE_BOOT_COMPLETED` and `FOREGROUND_SERVICE`. None of them can transmit anything while
`INTERNET` is absent. CI fails the build if the permission list changes without an explicit
decision.

## Encryption — the honest answer

**Bloom does not encrypt your data in Release 1.** It relies on the operating system's app
sandbox: other apps cannot read Bloom's private storage. On a rooted or compromised device,
or with a device backup, that protection is weaker. If you want more, protect the device with
your screen lock; an optional app lock is planned for Release 1.1 and will be described
accurately when it exists.

## Backup and export

Bloom does not upload anything. You can export your data to a file you choose; that file is
plain and readable, so store it accordingly. Android's own device backup may include Bloom's
data if your device is configured that way.

## Children

Bloom collects no personal data at all, so it collects none from children either.

## Changes

Any change to this statement will accompany a change to the app, and the app will not start
sending data without a release note saying so.

## Contact

Open an issue in this repository, or see [`SECURITY.md`](SECURITY.md) for anything sensitive.
