# Session Handoff — BODMAS Calculator

_Last updated: 4 Jul 2026. Scratch notes for resuming work; not part of the shipped app._

## Where we are

The 1.5.3 codebase is refactored and **building green**. AdMob has been **re-enabled** (banner + interstitial, test IDs in debug).

### Done (refactor session)
- **Architecture**: `CalculatorEngine` + `CalculatorState` extracted; `MainActivity` is a thin UI layer.
- **Tests**: `CalculatorEngineTest` + `CalculatorStateTest` (JUnit 4).
- **UI/API**: display `TextView`, auto-sizing text, View Binding, `OnBackPressedDispatcher`, gated logs.
- **Cleanup**: dead code, unused deps, orphaned layouts removed.
- **Docs**: `README.md`, `VERSION_HISTORY.md`, `website_documentation.md` updated.

### Done (AdMob session — 4 Jul 2026)
- Removed `AD_ID` permission `tools:node="remove"` from manifest.
- Wrapped layout: `LinearLayout` > `TableLayout` (weight 1) + bottom `AdView`.
- `MobileAds.initialize()` + banner load + interstitial preload/reload on dismiss.
- `$` and `₹` buttons trigger interstitial (`showInterstitialAd()`).
- Debug builds use Google **test** ad unit IDs; release builds use production IDs from `strings.xml`.
- Banner lifecycle: `pause` / `resume` / `destroy` in Activity callbacks.

### Git status
All changes remain **uncommitted** in the working tree.

## Before Play Store release (AdMob checklist)

- [ ] Replace `admob_app_id` in `strings.xml` with your **real** App ID (`~` format from AdMob console).
- [ ] Confirm production ad unit IDs (`ad_id_banner`, `interstitial_ad_unit_id`) are correct.
- [ ] Add **UMP / consent** (`com.google.android.ump:user-messaging-platform`) for EU/UK users.
- [ ] Update Play Console **Data safety** (advertising ID collected).
- [ ] Publish `app-ads.txt` on versionpb.co.in and link it in AdMob.

## Build tips (Windows)

If a build fails with `Unable to delete directory ... app\build`, a stale JVM is holding a lock:

```powershell
.\gradlew.bat --stop
Get-Process java -ErrorAction SilentlyContinue | Stop-Process -Force
Remove-Item -LiteralPath "app\build" -Recurse -Force -ErrorAction SilentlyContinue
```

A Defender exclusion for the `build` folders helps avoid this.

## Quick test commands

```powershell
.\gradlew.bat :app:testDebugUnitTest
.\gradlew.bat :app:assembleDebug
```

Run on device/emulator; in debug you should see **Test Ad** banner at bottom. Tap `$` or `₹` for test interstitial.
