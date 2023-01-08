import app.vahid.gradle.base.api
import app.vahid.gradle.base.testImplementation

plugins {
    id(ApplyPlugins.androidLibrary)
    id(ApplyPlugins.kotlinAndroid)
}

dependencies {
    testImplementation(Modules.CommonTestShared)
    api(Modules.CommonCore)

    api(
        Libraries.Presentation.orbitViewmodel,
        Libraries.Presentation.viewmodel,
    )
}

