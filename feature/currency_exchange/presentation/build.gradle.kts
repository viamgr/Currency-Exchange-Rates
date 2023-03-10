import app.vahid.gradle.base.api
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

    implementation(Modules.CommonCore, Modules.DomainUseCase)

    testImplementation(Modules.CommonTestShared)

    testImplementation(Libraries.Test.turbine)
    testImplementation(Libraries.Presentation.orbitMviTest, dependencyConfiguration = {
        exclude("org.jetbrains.kotlin")
        exclude("org.jetbrains.kotlinx")
    })

    api(Modules.CommonPresentation, Modules.DomainGateway)

    kapt(Libraries.Common.hiltCompiler)

    implementation(
        Libraries.Common.hiltAndroid,
    )

    implementation(Libraries.Presentation.orbitViewmodel, dependencyConfiguration = {
        exclude("org.jetbrains.kotlin")
        exclude("org.jetbrains.kotlinx")
    })

}

