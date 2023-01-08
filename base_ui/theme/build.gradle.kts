import app.vahid.gradle.base.apiA

plugins {
    id(ApplyPlugins.androidLibrary)
    id(ApplyPlugins.kotlinAndroid)
}
android {

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }

}

dependencies {

    apiA(
        Libraries.Ui.appcompat,
        Libraries.Ui.composeNavigation,
        Libraries.Ui.composeMaterial,
        Libraries.Ui.composeMaterial3,
        Libraries.Ui.composeConstraintlayout,
    )
}