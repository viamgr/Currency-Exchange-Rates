import app.vahid.gradle.base.androidTestImplementation
import app.vahid.gradle.base.implementation

plugins {
    id(ApplyPlugins.androidLibrary)
    id(ApplyPlugins.kotlinAndroid)
    id(ApplyPlugins.kotlinKapt)
    id(ApplyPlugins.daggerHiltPlugin)
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
    androidTestImplementation(Modules.BaseUiAndroidTestShared)

    implementation(
        Modules.DomainUseCase,
        Modules.FeatureCurrencyExchangePresentation,
        Modules.BaseUiCommon,
        Modules.BaseUiTheme
    )

    kapt(Libraries.Common.hiltCompiler)

    implementation(
        Libraries.Ui.composeMaterial,
        Libraries.Common.coroutineAndroid,
        Libraries.Common.hiltAndroid,
        Libraries.Ui.hiltHilt,
        Libraries.Ui.composeUi,
        Libraries.Ui.composePaging,
        Libraries.Ui.lifecycleRuntime,
        Libraries.Ui.composeUiTestManifest)
}