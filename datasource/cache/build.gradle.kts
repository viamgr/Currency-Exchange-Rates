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

    androidTestImplementation(
        Libraries.Test.junitTest,
        Libraries.Test.junitTest,
        Libraries.Test.testRunner,
        Libraries.Test.coroutinesTest,
        Libraries.Test.kotlinReflect,
        Libraries.Test.turbine,
    )

    implementation(
        Libraries.Common.coroutineAndroid,
        Libraries.Common.coroutineCore,
        Libraries.Common.hiltAndroid,
        Libraries.Cache.roomRuntime,
        Libraries.Cache.room,
        Libraries.Cache.roomPaging,
    )


}

