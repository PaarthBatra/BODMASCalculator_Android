# BODMAS Calculator for Android — Product Page Documentation

This document contains the updated copy and layout for your product page at [versionpb.co.in](https://versionpb.co.in/products/bodmas-calculator-for-android/). You can copy and paste the Markdown or HTML equivalent below directly into your website's editor.

---

# BODMAS Calculator for Android

**The Ultimate Expression Calculator for High-Precision Mathematics**

Welcome to the official home of the **BODMAS Calculator for Android** (Version 1.5.3). Built for students, engineers, and anyone tired of standard calculators failing on complex expressions, this application evaluates full mathematical equations following the strict rules of BODMAS precedence.

* **Google Play Store:** [Download App](https://play.google.com/store/apps/details?id=com.versionpb.bodmascalculator)
* **GitHub Repository:** [View Source Code](https://github.com/PaarthBatra/BODMASCalculator_Android)

---

## Why BODMAS Calculator?

Most standard built-in phone calculators evaluate equations step-by-step as you type, ignoring standard mathematical precedence rules, or force results into scientific notation (like `1.2E10`). 

BODMAS Calculator processes your **entire expression** as a single cohesive string, evaluating operators in their correct mathematical order:

$$\text{Brackets} \rightarrow \text{Orders/Exponents} \rightarrow \text{Division} \rightarrow \text{Multiplication} \rightarrow \text{Addition} \rightarrow \text{Subtraction}$$

### Visual Breakdown: Standard Calculator vs. BODMAS Calculator

| Equation | Default Android Calculator | VersionPB BODMAS Calculator | Correct Behavior |
| :--- | :--- | :--- | :--- |
| **`3 + 2 × 5`** | `25` *(Evaluates `3 + 2` first)* | **`13`** *(Respects multiplication first)* | **Correct (BODMAS)** |
| **`10,000,000,000 × 9,999,999,999`** | `1.0E20` *(Scientific notation)* | **`99999999990000000000`** *(Human-readable)* | **High Precision** |
| **`2 ^ 3 + (4 × 5)`** | Varies or requires scientific mode | **`28`** *(Evaluates exponent & brackets)* | **Correct (BODMAS)** |

---

## Key Features

### 1. High-Precision Calculator Engine
* **EvalEx Engine**: Powered by the advanced `EvalEx` library, using Java's `BigDecimal` with **128-bit precision** to prevent rounding errors on huge numbers or recurring decimals.
* **No Exponents (`E`)**: Large results are written out in full, human-readable numbers instead of confusing scientific notations.
* **Smart Decimal Trimming**: Displays up to 4 decimal places with auto-trimming for clean, clutter-free results.

### 2. Evolving & Responsive UI
* **Live Expression Display**: The top screen tracks the full equation as it expands (e.g. `12 + 34 × (5 - 2)`), while the bottom display shows the current input.
* **Auto-Shrinking Font Size**: As your expression grows longer, the text automatically shrinks (`40sp` $\rightarrow$ `20sp` $\rightarrow$ `15sp` $\rightarrow$ `8sp`) to ensure the entire equation remains visible without overlapping or wrapping off-screen.
* **Quick Theme Switcher (S)**: Easily toggle between two contrasting styles: Classic Professional Blue and Modern Vibrant Pink/Red.
* **Fully Responsive**: Built using a weighted layout that automatically scales to fit various screen sizes, ratios, and orientations (portrait and landscape).

### 3. Smart Usability Utilities
* **Intelligent Backspace (Del)**: Safely delete characters one by one. The app tracks state history to ensure backspace behaves logically across numbers, operators, brackets, and negative values.
* **Double-Back Exit**: Prevent accidentally closing the calculator mid-calculation. A quick double-tap on your device's back button is required to exit.

---

## Technical Specifications (v1.5.3)

* **Minimum SDK:** API 21 (Android 5.0 Lollipop)
* **Target SDK:** API 35 (Android 15)
* **Core Technology:** Written in Java (JDK 17 target compatibility)
* **Active Dependencies:** 
  * `androidx.appcompat:appcompat:1.7.0`
  * `com.udojava:EvalEx:2.7`
  * `com.google.android.flexbox:flexbox:3.0.0`

---

## Version History & Changelog

### v1.5.3 (Latest Release)
* **Android 15 Fix**: Patched a critical layout bug where display items were rendered behind the system status/notification bar on Android 15 (API 35) by correctly handling edge-to-edge window insets.
* **Autofill & Focus Fix**: Disabled predictive keyboard suggestions and touch focus events on the calculation display view.

### v1.5.2
* **Build Modernization**: Upgraded the compilation toolchain to Java 17 for better runtime efficiency on newer Android devices.
* **Bug Fixes**: Restructured root build directories to fix importing errors in Android Studio.
* **Warnings Cleanup**: Resolved obsolete option warnings and deprecated API references (e.g., migrated to `ContextCompat` and SDK conditional `Html.fromHtml` modes).

### v1.5.1
* **Link Update**: Updated footer hyperlink endpoints to `versionpb.co.in`.
* **AdMob APIs**: Migrated to the modern, non-blocking Google AdMob API client structure.

### v1.5.0
* Added exponentiation (`^`) operator support.
* Integrated the double-theme system switcher.
