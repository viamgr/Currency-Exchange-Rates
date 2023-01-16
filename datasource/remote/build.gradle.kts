import app.vahid.gradle.base.implementation
import app.vahid.gradle.base.kapt
import app.vahid.gradle.base.testImplementation

plugins {
    id(ApplyPlugins.androidLibrary)
    id(ApplyPlugins.kotlinAndroid)
    id(ApplyPlugins.kotlinKapt)
    id(ApplyPlugins.kotlinxSerialization)
    id(ApplyPlugins.daggerHiltPlugin)
}

android {
    defaultConfig {
        buildConfigField("String",
            ConfigData.encryptedBaseUrlKey,
            "\"${ConfigData.encryptedBaseUrl}\"")
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.18.1"
        }
    }
}

dependencies {
    testImplementation(Modules.CommonTestShared)
    implementation(Modules.CommonCore, Modules.Repository)

    kapt(Libraries.Common.hiltCompiler)
    implementation(
        Libraries.Network.retrofit2,
        Libraries.Network.okhttp3,
        Libraries.Network.okhttp3LoggingInterceptor,
        Libraries.Common.hiltAndroid,
        Libraries.Network.serializationJson,
        Libraries.Network.retrofitSerialization,
    )
}

