import app.vahid.gradle.base.api

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

    api(
        Libraries.Ui.appcompat,
        Libraries.Ui.composeNavigation,
        Libraries.Ui.composeMaterial,
        Libraries.Ui.composeMaterial3,
        Libraries.Ui.systemUiController,
        Libraries.Ui.composeConstraintlayout,
    )
}