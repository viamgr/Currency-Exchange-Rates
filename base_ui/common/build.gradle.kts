import app.vahid.gradle.base.api
import app.vahid.gradle.base.implementation

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

    api(Modules.BaseUiTheme, Modules.CommonCore, Modules.CommonPresentation)

    implementation(
        Libraries.Ui.composeMaterial3,
        Libraries.Ui.composeMaterial,
        Libraries.Ui.coil,
        Libraries.Ui.composeNavigation,
        Libraries.Ui.orbitCompose,
        Libraries.Ui.composeFoundation,
        Libraries.Ui.composeFoundationLayout,
        Libraries.Ui.composeUi,
    )
}