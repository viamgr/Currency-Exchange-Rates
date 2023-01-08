import app.vahid.gradle.base.implementation

plugins {
    id(ApplyPlugins.androidLibrary)
    id(ApplyPlugins.kotlinAndroid)
    id(ApplyPlugins.kotlinKapt)
    id(ApplyPlugins.daggerHiltPlugin)
}

dependencies {
    implementation(Modules.CommonCore, Modules.DomainGateway)

    implementation(
        Libraries.Common.hiltAndroid,
        Libraries.Common.hiltCompiler,
        Libraries.Cache.roomPaging,
    )
}