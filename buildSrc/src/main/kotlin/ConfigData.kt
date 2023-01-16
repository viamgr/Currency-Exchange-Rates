import org.gradle.api.JavaVersion

object ConfigData {
    const val compileSdkVersion = 33
    const val minSdkVersion = 21
    const val targetSdkVersion = 33
    const val versionCode = 1
    const val versionName = "0.1"
    const val appID = "app.vahid"
    val jvmTarget = JavaVersion.VERSION_11
    const val testBuildType = "staging"
    const val testInstrumentationRunner = "$appID.base_ui.android_test_shared.CustomTestRunner"
    const val encryptedBaseUrlKey = "ENCRYPTED_BASE_URL_KEY"
    const val encryptedBaseUrl =
        "ASxNzjOm4CmQM1i4yy8WD0Om/5VQOUCPnJA7OhX2dwnznhrLV+nkF0rEr+aTspFmcOczIgDnT2IlWIurFHPx27bkpVHBjrS+5t52DFFiKtUCfzb+asdTUHknCZUTIbimr7n9ukgOhAKNhegu+uSgtq5sj4U6gnbYQgYO48HDRiY="
}

