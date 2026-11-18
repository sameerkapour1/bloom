# Security policy

## Reporting a vulnerability

Please **do not** open a public issue for a security problem. Use GitHub's private
vulnerability reporting on this repository (Security → Report a vulnerability) so the
report stays private until there is a fix.

Include what you did, what you observed, the app version and your Android version. A
proof of concept helps but is not required.

## What Bloom protects, and what it does not

- Bloom stores habit, goal and completion data in the app's private storage. It **does not
  encrypt that data** in Release 1 and makes no such claim — see `PRIVACY.md`.
- Bloom requests no `INTERNET` permission, so the app cannot transmit data. Any change to
  that is a security-relevant event, not a routine dependency bump.
- Bloom does not implement custom cryptography. If that ever changes, it will use
  platform-backed key storage and a documented design, never home-made ciphers.
- Device compromise, rooted devices and OS-level backups are outside Bloom's threat model,
  and the app does not pretend otherwise.

## Supported versions

Bloom has no public release yet. Fixes target the latest commit on `main`.

## Handling of reports

You will get an acknowledgement, an assessment, and a fix or a clear explanation of why the
report is out of scope. Credit is given on request.
