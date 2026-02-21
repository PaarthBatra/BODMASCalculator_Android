# BODMAS Calculator — Android

A fully-featured BODMAS-compliant calculator for Android, written in Java and built with Android Studio. Available on the Google Play Store.

**Package:** `com.versionpb.bodmascalculator`  
**Current Version:** 1.5 (versionCode 5)  
**Min SDK:** 21 (Android 5.0 Lollipop)  
**Target SDK:** 34 (Android 14)

---

## Features

### Calculator Engine
- **Full BODMAS expression support** — evaluates complete multi-operator expressions respecting Brackets, Orders, Division, Multiplication, Addition, and Subtraction precedence
- **High-precision arithmetic** — uses `BigDecimal` with 128-bit precision via the [EvalEx](https://github.com/uklimaschewski/EvalEx) library
- **Live expression display** — a top `TextView` shows the full evolving expression (e.g. `12+34×`) while the bottom `EditText` shows the current operand (`34`)
- **Bracket support** — properly handles opening `(` and closing `)` brackets in complex expressions
- **Power operator (`^`)** — exponentiation support
- **Decimal input** — handles decimal points with duplicate-dot prevention
- **Backspace (Del)** — intelligent delete that correctly removes operands and operators across multiple states
- **Clear (C)** — resets the full expression and result
- **Automatic decimal trimming** — results are shown with up to 4 decimal places, with trailing zeros removed

### UI
- **Responsive layout** — `TableLayout` with `layout_weight` rows that adapt to screen height
- **Portrait-locked orientation** — `android:screenOrientation="portrait"` for consistent layout
- **Dynamic text sizing** — the expression `TextView` automatically shrinks its font size (40sp → 20sp → 15sp → 8sp) as the expression grows longer to prevent overflow
- **Theme skinning** — press the `S` button to toggle between two color themes (blue and pink/red), which recolor all buttons, the action bar, and the display area
- **Clickable link** — footer `TextView` shows a clickable hyperlink to [versionpb.com](https://www.versionpb.com)
- **Double-back to exit** — pressing Back twice within 2 seconds exits the app, with a toast message on the first press

### Advertising (AdMob)
- **Banner ad** — persistent banner at the bottom of the screen (AdMob)
- **Interstitial ad** — full-screen interstitial ad, triggered via the `$` and `₹` buttons, with auto-reload on dismissal

---

## Project Structure

```
app/src/main/
├── AndroidManifest.xml              # App manifest, permissions, activity declarations
├── java/com/versionpb/bodmascalculator/
│   └── MainActivity.java            # All calculator logic and UI wiring (single Activity)
└── res/
    ├── layout/activity_main.xml     # Portrait layout (TableLayout, 9 rows)
    ├── layout-land/activity_main.xml# Landscape layout variant
    ├── drawable/button_s.xml        # Button selector drawable
    ├── drawable/button_text_colour.xml
    ├── values/strings.xml           # String resources and AdMob unit IDs
    ├── values/colors.xml            # Color palette
    └── values/styles.xml            # App theme
```

---

## Key Dependencies

| Library | Version | Purpose |
|---|---|---|
| `androidx.appcompat:appcompat` | 1.7.0 | AppCompatActivity, action bar |
| `androidx.core:core` | 1.13.1 | AndroidX core utilities |
| `com.udojava:EvalEx` | 2.7 | BODMAS math expression evaluator |
| `com.google.android.gms:play-services-ads` | 23.4.0 | AdMob banner + interstitial ads |
| `com.google.android.flexbox:flexbox` | 3.0.0 | Flexbox layout support |

---

## Build Setup

| Setting | Value |
|---|---|
| Gradle Wrapper | 8.7 |
| Android Gradle Plugin | 8.3.2 |
| `compileSdk` | 34 |
| `targetSdk` | 34 |
| `minSdk` | 21 |
| Java compatibility | 1.8 |

---

## Version History

See [VERSION_HISTORY.md](VERSION_HISTORY.md) for a full changelog.
