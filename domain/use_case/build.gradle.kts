import app.vahid.gradle.base.api
import app.vahid.gradle.base.kapt
import app.vahid.gradle.base.testImplementation


plugins {
    id(ApplyPlugins.androidLibrary)
    id(ApplyPlugins.kotlinAndroid)
    id(ApplyPlugins.kotlinKapt)
    id(ApplyPlugins.daggerHiltPlugin)
}

dependencies {
    api(Modules.DomainGateway, Modules.CommonDomainCommon)
    testImplementation(Modules.CommonTestShared)

    kapt(Libraries.Common.hiltCompiler)

    implementation(
        Libraries.Common.hiltAndroid,
    )
}

