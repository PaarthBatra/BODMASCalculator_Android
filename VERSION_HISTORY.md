# Version History

---

## [1.5.3] — 31 May 2026

### Fixed
- Fixed critical layout overlap on Android 15 devices where calculation inputs were rendered behind the system status/notification bar. Enabled `fitsSystemWindows` on the root layouts.
- Disabled system-level keyboard suggestions, autofill indexing, and unwanted touch focus overlay triggers on the main display text field.
- `versionCode` bumped from 7 → 8

---

## [1.5.2] — 31 May 2026

### Changed
- Commented out AdMob advertising (Banner and Interstitial ads) from the layout and code logic to prevent unnecessary ad requests during local build/development.

### Fixed
- Fixed project loading issue in Android Studio by restructuring the directory layout (moved nested project files to root).
- Upgraded targetSdk and compileSdk versions to 35 (Android 15) to meet Google Play Console requirements.
- Upgraded Java compiler compatibility version from Java 8 to Java 17.
- Resolved compile-time warnings and deprecated API usages (`ContextCompat.getColor`, conditional version checks for `Html.fromHtml`, and `@SuppressWarnings` for onBackPressed).
- `versionCode` bumped from 6 → 7

---

## [1.5.1] — 21 Feb 2026

### Changed
- Footer link updated from `www.versionpb.com` to `www.versionpb.co.in`
- Version display on calculator header changed from `Ver 1.5` → `V 1.5.1`
- `versionCode` bumped from 5 → 6

---

## [Build Modernisation — Gradle & AndroidX Migration] — 21 Feb 2026

Major upgrade of the entire build toolchain and dependency stack. No functional changes to the calculator logic.

### Build Toolchain
| Component | Before | After |
|---|---|---|
| Gradle Wrapper | 4.1 | **8.7** |
| Android Gradle Plugin (AGP) | 3.0.0 | **8.3.2** |
| `compileSdk` / `targetSdk` | 26 | **34** |
| `minSdk` | 15 | **21** |
| Java compatibility | — | **1.8** (explicit) |

### Dependencies Migrated
| Dependency | Before | After |
|---|---|---|
| `android.support:appcompat-v7` | 26.1.0 | `androidx.appcompat:appcompat` **1.7.0** |
| `android.support:support-v4` | 26.0.+ | `androidx.core:core` **1.13.1** |
| `android.support.constraint:constraint-layout` | 1.1.0 | `androidx.constraintlayout:constraintlayout` **2.1.4** |
| `play-services-ads` | 8.4.0 | **23.4.0** |
| `EvalEx` | 2.0 | **2.7** |
| `com.google.android:flexbox` | 1.0.0 | `com.google.android.flexbox:flexbox` **3.0.0** |
| Test runner | `android.support.test` | `androidx.test` **1.6.2** |
| Espresso | `android.support.test.espresso` 3.0.1 | `androidx.test.espresso` **3.6.1** |

### Repository Changes
- Removed `jcenter()` (deprecated and shut down)
- Removed redundant `maven { url "https://maven.google.com" }` block (now included in `google()`)
- All repositories now resolved from `google()` + `mavenCentral()`

### Source Code Changes (`MainActivity.java`)
- Migrated `android.support.v4.content.ContextCompat` → `androidx.core.content.ContextCompat`
- Migrated `android.support.v7.app.AppCompatActivity` → `androidx.appcompat.app.AppCompatActivity`
- **Interstitial Ad API migration** (AdMob 20+ breaking change):
  - Removed deprecated `InterstitialAd` constructor and `setAdListener()`
  - Migrated to `InterstitialAd.load()` static method with `InterstitialAdLoadCallback`
  - Added `FullScreenContentCallback` for ad lifecycle (replaces `onAdClosed`)
  - Updated `mInterstitialAd.show()` → `mInterstitialAd.show(Activity)` with null guard
- Removed deprecated `AdRequest.Builder().addTestDevice()` calls

### Manifest Changes (`AndroidManifest.xml`)
- Removed `package=` attribute from `<manifest>` root (now declared via `namespace` in `build.gradle`, required by AGP 8+)
- Added `android:exported="true"` to `MainActivity` (required for targetSdk ≥ 31 when an `<intent-filter>` is present)

### Test Changes (`ExampleInstrumentedTest.java`)
- Migrated `android.support.test.InstrumentationRegistry` → `androidx.test.platform.app.InstrumentationRegistry`
- Migrated `android.support.test.runner.AndroidJUnit4` → `androidx.test.ext.junit.runners.AndroidJUnit4`
- Updated `InstrumentationRegistry.getTargetContext()` → `InstrumentationRegistry.getInstrumentation().getTargetContext()`

### Infrastructure
- `gradle.properties`: Added `android.useAndroidX=true`, `android.enableJetifier=true`; bumped JVM heap `1536m` → `2048m`
- `local.properties`: Updated `sdk.dir` from stale `D:\Android\sdk` to the current SDK path

---

## Ver 1.5 — 1 Nov 2018

- Added AdMob Banner Ad at the bottom of the screen

## Ver 1.4 — (undocumented)

- Internal improvements

## Ver 1.3 — 1 Nov 2018

- Addition of AdMob Banner Ad (original entry in source comments)

## Ver 1.0–1.2 — (pre-2018)

- Initial release with core BODMAS calculator functionality
- Basic operations: `+`, `-`, `×`, `÷`, `^`, brackets, decimal
- Theme skinning (two colour themes)
- Double-back-to-exit behaviour
