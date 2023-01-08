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
    const val baseUrlKey = "BASE_URL"
}

