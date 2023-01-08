import app.vahid.gradle.base.androidTestImplementation
import app.vahid.gradle.base.configureBuildTypes
import app.vahid.gradle.base.configurePackagingOptions
import app.vahid.gradle.base.implementation
import app.vahid.gradle.base.testImplementation

plugins {
    id(ApplyPlugins.androidApplication)
    id(ApplyPlugins.kotlinAndroid)
    id(ApplyPlugins.kotlinKapt)
    id(ApplyPlugins.daggerHiltPlugin)
}

kapt {
    correctErrorTypes = true
    useBuildCache = true
    correctErrorTypes = true
}

hilt {
    enableAggregatingTask = true
}

android {

    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = ConfigData.appID
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

        vectorDrawables {
            useSupportLibrary = true
        }
        testInstrumentationRunner = ConfigData.testInstrumentationRunner
    }

    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }

    configureBuildTypes()

    configurePackagingOptions()

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }
    namespace = ConfigData.appID
}

dependencies {

    testImplementation(Modules.CommonTestShared)

    implementation(
        Modules.CommonCore,
        Modules.BaseUiCommon,
        Modules.BaseUiTheme,
        Modules.FeatureCurrencyExchangeUi,
        Modules.Repository,
        Modules.DatasourceRemote,
        Modules.DatasourceCache,
        Modules.DatasourceCache,
    )

    androidTestImplementation(Modules.BaseUiAndroidTestShared)

    androidTestImplementation(
        Libraries.Test.hiltAndroidTest,
        Libraries.Test.junitTest,
        Libraries.Common.hiltCompiler
    )

    implementation(
        Libraries.Ui.appcompat,
        Libraries.Ui.composeActivity,
        Libraries.Common.hiltAndroid,
        Libraries.Common.hiltCompiler,
    )
}