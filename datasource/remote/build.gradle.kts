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

