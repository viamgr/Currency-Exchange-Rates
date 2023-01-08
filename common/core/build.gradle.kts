import app.vahid.gradle.base.apiA
import app.vahid.gradle.base.testImplementation

plugins {
    id(ApplyPlugins.androidLibrary)
    id(ApplyPlugins.kotlinAndroid)
}

dependencies {

    testImplementation(Modules.CommonTestShared)

    apiA(
        Libraries.Common.timber,
        Libraries.Common.hiltAndroid,
        Libraries.Common.coroutineAndroid,
        Libraries.Common.coroutineCore
    )

}
