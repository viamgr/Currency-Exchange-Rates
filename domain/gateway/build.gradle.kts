import app.vahid.gradle.base.implementation

plugins {
    id(ApplyPlugins.androidLibrary)
    id(ApplyPlugins.kotlinAndroid)
}
dependencies {

    implementation(Modules.CommonCore)

    implementation(Libraries.Cache.roomPaging)
}