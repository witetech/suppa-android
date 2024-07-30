# Suppa - Android SDK

[ ![Download](https://img.shields.io/maven-central/v/ai.suppa/suppa-android?label=Download) ](https://central.sonatype.com/artifact/ai.suppa/suppa-android)

<img alt="cover" src="images/cover.png">

The Suppa Android SDK is a powerful library designed to simplify the integration of [Suppa](https://www.suppa.ai)’s services into your Android applications.

## Download & Quick Start

1. Add the following dependency to your `build.gradle` file:
```kotlin
implementation("ai.suppa:suppa-android:0.0.1")
```

2. Use the `launchSuppa` extension in your `Activity` with the API key you receive from your [Dashboard](https://app.suppa.ai/dashboard/settings)
```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        launchSuppa(apiKey = "your-api-key")
    }
}
```
