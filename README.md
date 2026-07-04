# BODMAS Calculator — Android

A fully-featured BODMAS-compliant calculator for Android, written in Java and built with Android Studio. Available on the Google Play Store.

**Package:** `com.versionpb.bodmascalculator`  
**Current Version:** 1.5.3 (versionCode 9)  
**Min SDK:** 21 (Android 5.0 Lollipop)  
**Target SDK:** 35 (Android 15)

---

## Features

### Calculator Engine
- **Full BODMAS expression support** — evaluates complete multi-operator expressions respecting Brackets, Orders, Division, Multiplication, Addition, and Subtraction precedence
- **High-precision arithmetic** — uses `BigDecimal` with 128-bit precision via the [EvalEx](https://github.com/uklimaschewski/EvalEx) library
- **Live expression display** — a top `TextView` shows the full evolving expression (e.g. `12+34×`) while a bottom (display-only) `TextView` shows the current operand (`34`)
- **Bracket support** — properly handles opening `(` and closing `)` brackets in complex expressions
- **Power operator (`^`)** — exponentiation support
- **Decimal input** — handles decimal points with duplicate-dot prevention
- **Backspace (Del)** — intelligent delete that correctly removes operands and operators across multiple states
- **Clear (C)** — resets the full expression and result
- **Automatic decimal trimming** — results are shown with up to 4 decimal places, with trailing zeros removed

### UI
- **Responsive layout** — `TableLayout` with `layout_weight` rows that adapt to screen height
- **Portrait-locked orientation** — `android:screenOrientation="portrait"` for consistent layout
- **Auto-sizing text** — the expression `TextView` uses native uniform auto-sizing (`autoSizeTextType="uniform"`, 8sp–40sp) so the font scales automatically as the expression grows, preventing overflow without any manual font-size code
- **Theme skinning** — press the `S` button to toggle between two color themes (blue and pink/red), which recolor all buttons, the action bar, and the display area
- **Clickable link** — footer `TextView` shows a clickable hyperlink to [versionpb.co.in](https://www.versionpb.co.in)
- **Modern back handling** — exit is handled via `OnBackPressedDispatcher`; pressing Back twice within 2 seconds exits the app, with a toast message on the first press

### Advertising (AdMob)
- The `play-services-ads` dependency is still present, but all banner/interstitial ad code is currently **disabled** (removed from `MainActivity` during cleanup). The `$` and `₹` buttons remain in the layout but are no longer wired to any ad action.

### Architecture & Testing
- **Separation of concerns** — calculator logic lives in two plain-Java, unit-tested classes:
  - `CalculatorEngine` — pure evaluation: operator normalization (`X`/`÷` → `*`/`/`), `EvalEx` integration, result formatting (4-dp truncation, trailing-zero trimming, no scientific notation), and trailing-operator stripping
  - `CalculatorState` — input/state machine for digits, operators, brackets, decimals, backspace, and clear; returns a `UiUpdate` hint (`NONE`/`EDIT_ONLY`/`TEXT_ONLY`/`BOTH`) so the UI only redraws what changed
- **`MainActivity`** is now a thin UI layer that wires views via **View Binding** and delegates all input to `CalculatorState`
- **Unit tests** (JUnit 4) cover both classes — see `CalculatorEngineTest` and `CalculatorStateTest`. Run with `gradlew :app:testDebugUnitTest`

---

## Project Structure

```
app/src/
├── main/
│   ├── AndroidManifest.xml          # App manifest, permissions, activity declarations
│   ├── java/com/versionpb/bodmascalculator/
│   │   ├── MainActivity.java        # Thin UI layer: View Binding wiring + delegation
│   │   ├── CalculatorEngine.java    # Pure expression evaluation & result formatting
│   │   └── CalculatorState.java     # Input/state machine (digits, ops, brackets, del)
│   └── res/
│       ├── layout/activity_main.xml # Portrait layout (TableLayout, weighted rows)
│       ├── drawable/button_s.xml    # Button selector drawable
│       ├── drawable/button_text_colour.xml
│       ├── values/strings.xml       # String resources
│       ├── values/colors.xml        # Color palette
│       └── values/styles.xml        # App theme
└── test/java/com/versionpb/bodmascalculator/
    ├── CalculatorEngineTest.java    # Unit tests for the evaluation engine
    └── CalculatorStateTest.java     # Unit tests for the input state machine
```

---

## Key Dependencies

| Library | Version | Purpose |
|---|---|---|
| `androidx.appcompat:appcompat` | 1.7.0 | AppCompatActivity, action bar |
| `androidx.core:core` | 1.13.1 | AndroidX core utilities |
| `com.udojava:EvalEx` | 2.7 | BODMAS math expression evaluator |
| `com.google.android.gms:play-services-ads` | 23.4.0 | AdMob SDK (ad code currently disabled) |
| `junit:junit` (test) | 4.13.2 | Unit tests for engine + state |

> Removed during cleanup: `androidx.constraintlayout:constraintlayout` and `com.google.android.flexbox:flexbox` (unused).

---

## Build Setup

| Setting | Value |
|---|---|
| Gradle Wrapper | 8.13 |
| Android Gradle Plugin | 8.13.2 |
| `compileSdk` | 35 |
| `targetSdk` | 35 |
| `minSdk` | 21 |
| Java compatibility | 17 |
| View Binding | enabled (`buildFeatures.viewBinding`) |
| BuildConfig | enabled (`buildFeatures.buildConfig`) |

### Common commands

```bash
gradlew :app:testDebugUnitTest   # run unit tests
gradlew :app:assembleDebug       # build debug APK
gradlew :app:assembleRelease     # build release APK (unsigned)
```

> **Windows note:** if a build fails with `Unable to delete directory ... app\build`, a stale Gradle/Kotlin JVM is holding a file lock (often left over after closing Android Studio). Stop it with `gradlew --stop`, or kill leftover processes via `Get-Process java | Stop-Process -Force`, then rebuild. Adding a Defender exclusion for the `build` folders helps avoid this.

---

## Version History

See [VERSION_HISTORY.md](VERSION_HISTORY.md) for a full changelog.
