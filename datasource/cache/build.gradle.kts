import app.vahid.gradle.base.androidTestImplementation
import app.vahid.gradle.base.implementation
import app.vahid.gradle.base.kapt
import app.vahid.gradle.base.testImplementation

plugins {
    id(ApplyPlugins.androidLibrary)
    id(ApplyPlugins.kotlinAndroid)
    id(ApplyPlugins.kotlinKapt)
    id(ApplyPlugins.daggerHiltPlugin)
}
dependencies {

    androidTestImplementation(Modules.BaseUiAndroidTestShared)

    testImplementation(Modules.CommonTestShared)

    implementation(Modules.CommonCore, Modules.Repository)

    kapt(Libraries.Cache.roomCompiler, Libraries.Common.hiltCompiler)

    implementation(
        Libraries.Common.hiltAndroid,
        Libraries.Cache.roomRuntime,
        Libraries.Cache.room,
        Libraries.Cache.roomPaging,
    )


}

