import app.vahid.gradle.base.api
import app.vahid.gradle.base.testImplementation

plugins {
    id(ApplyPlugins.androidLibrary)
    id(ApplyPlugins.kotlinAndroid)
}

dependencies {

    testImplementation(Modules.CommonTestShared)

    api(
        Libraries.Common.timber,
        Libraries.Common.hiltAndroid,
        Libraries.Common.coroutineAndroid,
        Libraries.Common.coroutineCore
    )

}
