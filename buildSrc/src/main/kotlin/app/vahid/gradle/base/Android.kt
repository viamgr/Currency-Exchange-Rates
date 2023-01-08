package app.vahid.gradle.base

import ConfigData
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.TestedExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

fun Project.configureAndroid() =
    this.extensions.getByType<LibraryExtension>().run {

        namespace = "${ConfigData.appID}${path.replace(":", ".")}"

        compileSdk = ConfigData.compileSdkVersion

        defaultConfig {
            minSdk = ConfigData.minSdkVersion
            targetSdk = ConfigData.targetSdkVersion

            vectorDrawables {
                useSupportLibrary = true
            }
            testInstrumentationRunner = ConfigData.testInstrumentationRunner
        }

        testOptions {
            unitTests.all {
                it.useJUnitPlatform()
            }
        }

        configureBuildTypes()

        configurePackagingOptions()
    }

fun TestedExtension.configurePackagingOptions() {
    packagingOptions {
        resources {
            excludes += listOf(
                "**/attach_hotspot_windows.dll",
                "META-INF/**",
                "/META-INF/{AL2.0,LGPL2.1}",
                "META-INF/DEPENDENCIES",
                "META-INF/LICENSE",
                "META-INF/LICENSE.md",
                "META-INF/LICENSE.txt",
                "META-INF/license.txt",
                "META-INF/LICENSE-notice.md",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
                "META-INF/notice.txt",
                "META-INF/ASL2.0",
                "META-INF/*.kotlin_module",
            )
        }
    }
}

fun Project.configureBuildTypes() = with(this.extensions.getByType<TestedExtension>()) {

    testBuildType = ConfigData.testBuildType

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }

        create(ConfigData.testBuildType) {
            initWith(getByName("debug"))
        }
    }
}
