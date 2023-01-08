import app.vahid.gradle.base.implementation

plugins {
    id(ApplyPlugins.androidLibrary)
    id(ApplyPlugins.kotlinAndroid)
}

dependencies {
    implementation(Modules.CommonCore)

    api(kotlin("test-junit5"))

    implementation(
        Libraries.Common.hiltCompiler,
        Libraries.Test.testRunner,
        Libraries.Ui.appcompat,
        Libraries.Test.hiltAndroidTest,
    )
}

