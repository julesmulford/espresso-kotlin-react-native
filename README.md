# espresso-kotlin-rn

> **GitHub repo description:** Android UI test framework for React Native apps — Kotlin, Espresso, AndroidX Test, JUnit 4, Screen Object Pattern, Allure Android, screenshot-on-failure.

A production-grade Android UI automation framework using Espresso and Kotlin, targeting React Native apps. Demonstrates the Screen Object Pattern, IdlingResource-based synchronization for React Native's async rendering, screenshot capture on failure, and Allure Android reporting.

## Tech Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| Language | Kotlin | 1.9.23 |
| Build | Gradle (Kotlin DSL) | 8.x |
| UI Testing | Espresso Core | 3.5.1 |
| Test Runner | AndroidX Test Runner | 1.5.2 |
| Test Rules | AndroidX Test Rules | 1.5.0 |
| Test Framework | JUnit 4 | 4.13.2 |
| Reporting | Allure Android | 2.6.0 |
| Android SDK | Min 24, Target/Compile 34 | — |

## Why JUnit 4 over JUnit 5 for Android?

JUnit 4 remains the standard choice for Android instrumented tests because:
- `AndroidJUnit4` runner is the official Android test runner built for JUnit 4
- `@Rule` annotations (`ActivityScenarioRule`, screenshot rule) depend on JUnit 4's rule system
- JUnit 5 for Android requires additional setup and has limited official support for instrumented tests
- All official Android documentation and Espresso samples use JUnit 4

## Why No Allure-First Reporting?

Allure for Android (`io.qameta.allure:allure-android`) has constraints:
- Requires writing results to device storage and pulling them via `adb`
- The CI setup is non-trivial compared to JVM-based Allure

This framework provides:
1. **Allure Android** integration (`allure-espresso`) where feasible
2. **Screenshot capture** via `ScreenshotRule` saved to device storage
3. **Spoon** or `testng-html`-compatible output via standard `AndroidJUnitRunner`
4. Standard JUnit XML reports (pulled via `adb pull`) which integrate with any CI reporting tool

See the **Reporting** section for the full reporting strategy.

## Target App

This framework is structured around the [Sauce Labs MyDemoApp React Native](https://github.com/saucelabs/my-demo-app-rn) sample app. It tests:
- Login flow
- Product listing
- Product details
- Add to cart
- Cart and checkout

The APK can be built from the [source repository](https://github.com/saucelabs/my-demo-app-rn) or downloaded from Sauce Labs releases.

## Project Structure

```
espresso-kotlin-react-native/
├── .github/workflows/ci.yml
├── app/
│   ├── build.gradle.kts
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   └── kotlin/com/saucelabs/mydemoapp/
│       │       └── MainActivity.kt      (stub - the real app is tested as a black box)
│       └── androidTest/
│           └── kotlin/com/saucelabs/tests/
│               ├── base/
│               │   └── BaseTest.kt
│               ├── screens/
│               │   ├── LoginScreen.kt
│               │   ├── ProductListScreen.kt
│               │   ├── ProductDetailScreen.kt
│               │   ├── CartScreen.kt
│               │   └── CheckoutScreen.kt
│               ├── utils/
│               │   ├── ScreenshotRule.kt
│               │   ├── WaitUtils.kt
│               │   └── EspressoUtils.kt
│               ├── testdata/
│               │   └── TestData.kt
│               └── tests/
│                   ├── LoginTest.kt
│                   ├── ProductTest.kt
│                   ├── CartTest.kt
│                   └── CheckoutTest.kt
├── build.gradle.kts              (root)
├── settings.gradle.kts
├── gradle.properties
├── gradle/wrapper/
│   └── gradle-wrapper.properties
├── .gitignore
└── README.md
```

## Prerequisites

- **Android Studio** (latest stable) or Android SDK command-line tools
- **Java 17+** (required by Android Gradle Plugin)
- **Android emulator** (API 29+) or physical device connected via ADB
- Gradle wrapper is bundled — no separate Gradle installation needed

## Setup

```bash
git clone https://github.com/YOUR_USERNAME/espresso-kotlin-rn.git
cd espresso-kotlin-rn

# Verify device/emulator is connected
adb devices

# Build and install the app under test (if you have the APK)
adb install path/to/MyDemoApp.apk
```

## Running Tests

```bash
# Run all instrumented tests
./gradlew connectedAndroidTest

# Run a specific test class
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.saucelabs.tests.LoginTest

# Run tests tagged as smoke
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.annotation=com.saucelabs.tests.annotations.Smoke

# Run on a specific emulator
ANDROID_SERIAL=emulator-5554 ./gradlew connectedAndroidTest

# Pull Allure results from device
adb pull /sdcard/allure-results ./allure-results

# Generate Allure report
allure serve allure-results
```

## GitHub Actions (CI)

The CI workflow:
1. Creates an Android emulator (API 29, x86_64)
2. Waits for it to boot
3. Runs `connectedAndroidTest`
4. Pulls test results and screenshots from the device
5. Uploads as artifacts

## React Native Synchronization

React Native renders asynchronously using the JS bridge (or JSI in newer versions). This causes synchronization issues with Espresso's standard `IdlingResource` model.

**Strategies used in this framework:**
1. `ReactNativeIdlingResource` — registers an `IdlingResource` that waits until the React Native bridge is idle
2. `WaitUtils.waitForView()` — polls for view visibility using `ViewInteraction` with retry
3. `EspressoUtils.waitForElement()` — combines `ViewMatcher` + polling for elements that load asynchronously
4. Generous `@Before` setup time via `SystemClock.sleep()` on first launch (configurable)

See `WaitUtils.kt` and the base test setup for the full synchronization strategy.

## Reporting Strategy

| Artifact | How to collect | Where it goes |
|----------|----------------|--------------|
| JUnit XML | `./gradlew connectedAndroidTest` generates to `app/build/outputs/androidTest-results/` | CI artifacts |
| Screenshots | `ScreenshotRule` saves PNG to `/sdcard/Pictures/TestScreenshots/` on device | `adb pull` in CI |
| Allure results | `allure-espresso` writes to `/sdcard/allure-results/` | `adb pull` + `allure serve` |
| Logcat | `adb logcat -d` during test run | CI artifacts |

## Scaling Notes

- For parallel test execution across multiple emulators, use Gradle's `maxParallelForks` or Firebase Test Lab
- For device farms, the framework is compatible with Sauce Labs Espresso Runner and BrowserStack App Automate
- The Screen Object Pattern makes it straightforward to add new screens without modifying existing tests
