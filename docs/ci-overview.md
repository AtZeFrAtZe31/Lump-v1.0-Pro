# CI / CD Overview

## Workflows
- android.yml: Builds debug + unsigned release on PR/main pushes; produces signed release & GitHub Release + optional distribution on version tag (v*).
- codeql.yml: Weekly and per-change security analysis for Java/Kotlin.

## Build Matrix
| Variant | Purpose |
|---------|---------|
| debug | Developer / Test feedback, unit tests, lint, detekt |
| releaseUnsigned | Pre-flight release build (no signing) |

## Version Extraction
Runs `:app:properties` and parses versionName/versionCode to version artifact names & release titles.

## Artifacts
- apk-debug-{version}-{code}: Debug APK (7 days retention)
- apk-release-unsigned-{version}-{code}: Unsigned release APK (10 days retention)
- release-signed-{version}-{code}: Signed APK + AAB for release tags (30 days retention)

## Optional Integrations
Set secrets to enable:
- Google Play (internal track): GOOGLE_PLAY_SERVICE_ACCOUNT_JSON or GOOGLE_PLAY_SERVICE_ACCOUNT_JSON_BASE64
- Sentry releases: SENTRY_AUTH_TOKEN, SENTRY_ORG, SENTRY_PROJECT
- Crashlytics mapping: Auto-detected if task exists

## MODULE_PATH Configuration
Current: `app`. If using different module structure, update `MODULE_PATH` env var in workflow.

## Detekt Setup
```kotlin
plugins { id("io.gitlab.arturbosch.detekt") version "1.23.6" apply false }
```
Then in module:
```kotlin
plugins { id("io.gitlab.arturbosch.detekt") }
```
Provide a detekt.yml (optional). Workflow auto-runs if task exists.

## CodeQL Notes
Autobuild attempts a default Gradle build. If it fails (complex configuration), replace Autobuild step with explicit `./gradlew :app:assembleDebug`.

## Future Enhancements
- Instrumentation tests with emulator (AVD) matrix.
- Publishing to Play other tracks (alpha/beta) with rollout.
- SBOM / Dependency review actions.
- Caching Gradle wrapper distribution via actions/cache if needed.

## Troubleshooting
- Version shows 'unknown': ensure versionName/versionCode defined in module's gradle config.
- Release job aborts: check required secrets present.
- Google Play upload skipped: verify service account secret.

## Wrapper
**TODO**: Gradle wrapper must be committed (gradlew, gradlew.bat, gradle/wrapper/*). Currently missing from repository. Regenerate locally: `gradle wrapper --gradle-version 8.9` and commit. The CI workflow assumes wrapper is present and will fail without it.