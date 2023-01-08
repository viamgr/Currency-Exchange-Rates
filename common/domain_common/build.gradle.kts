import app.vahid.gradle.base.api
import app.vahid.gradle.base.apiA
import app.vahid.gradle.base.testImplementation

plugins {
    id(ApplyPlugins.androidLibrary)
    id(ApplyPlugins.kotlinAndroid)
}

dependencies {

    api(Modules.CommonCore)
    testImplementation(Modules.CommonTestShared)

    apiA(
        Libraries.Common.coroutineCore,
    )
}

