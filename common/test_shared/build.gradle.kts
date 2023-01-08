import app.vahid.gradle.base.apiA
import app.vahid.gradle.base.implementation

plugins {
    id(ApplyPlugins.androidLibrary)
    id(ApplyPlugins.kotlinAndroid)
}

dependencies {
    implementation(Modules.CommonCore)

    api(kotlin("test-junit5"))

    apiA(
        Libraries.Test.junitTest,
        Libraries.Test.coroutinesTest,
        Libraries.Test.kotestRunner,
        Libraries.Test.kotlinReflect,
        Libraries.Test.kotestAssertions,
        Libraries.Test.turbine,
        Libraries.Test.mockk,
        Libraries.Test.mockkJvm,
    )
}

