object Libraries {

    object Test {
        const val coroutinesTest =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesAndroidVersion"
        const val junit5 = "org.jetbrains.kotlin:kotlin-test-junit5:$kotlinVersion"
        const val turbine = "app.cash.turbine:turbine:$turbineVersion"
        const val kotestRunner = "io.kotest:kotest-runner-junit5-jvm:$kotestJUnitVersion"
        const val kotestAssertions =
            "io.kotest:kotest-assertions-core-jvm:$kotestJUnitVersion"
        const val hiltAndroidTest =
            "com.google.dagger:hilt-android-testing:$hiltAndroidCompilerVersion"
        const val junitTest = "androidx.test.ext:junit:$androidXJunitVersion"
        const val testRunner = "androidx.test:runner:$androidXTestVersion"
        const val testRules = "androidx.test:rules:$androidXTestVersion"
        const val mockkJvm = "io.mockk:mockk-agent-jvm:$mockkVersion"
        const val mockk = "io.mockk:mockk:$mockkVersion"
        const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"
        const val junitJupiter = "org.junit.jupiter:junit-jupiter-api:$junitVersion"

    }

    object Ui {
        const val coil = "io.coil-kt:coil-compose:$coilVersion"
        const val composeFoundationLayout =
            "androidx.compose.foundation:foundation-layout:$composeVersion"
        const val composeFoundation =
            "androidx.compose.foundation:foundation:$composeVersion"
        const val composeUi = "androidx.compose.ui:ui:$composeVersion"
        const val appcompat = "androidx.appcompat:appcompat:$appcompatVersion"
        const val composeActivity =
            "androidx.activity:activity-compose:$activityComposeVersion"
        const val composeMaterial3 =
            "androidx.compose.material3:material3:$material3Version"
        const val composeMaterial =
            "androidx.compose.material:material:$materialVersion"
        const val composeConstraintlayout =
            "androidx.constraintlayout:constraintlayout-compose:$constraintLayoutVersion"
        const val lifecycleRuntime =
            "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
        const val composePaging =
            "androidx.paging:paging-compose:$pagingComposeVersion"
        const val composeNavigation =
            "androidx.navigation:navigation-compose:$navVersion"
        const val composeUiTestJunit4 =
            "androidx.compose.ui:ui-test-junit4:$composeVersion"
        const val composeUiTestManifest =
            "androidx.compose.ui:ui-test-manifest:$composeVersion"
        const val orbitCompose = "org.orbit-mvi:orbit-compose:$orbitVersion"
        const val mockkAndroid = "io.mockk:mockk-android:$mockkVersion"
        const val hiltHilt =
            "androidx.hilt:hilt-navigation-compose:$hiltComposeNavigationVersion"
    }

    object Presentation {
        const val orbitViewmodel = "org.orbit-mvi:orbit-viewmodel:$orbitVersion"
        const val orbitMviTest = "org.orbit-mvi:orbit-test:$orbitVersion"

        const val viewmodel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    }

    object Cache {
        const val roomRuntime = "androidx.room:room-runtime:$roomDBVersion"
        const val roomPaging = "androidx.room:room-paging:$roomDBVersion"
        const val roomCompiler = "androidx.room:room-compiler:$roomDBVersion"
        const val room = "androidx.room:room-ktx:$roomDBVersion"
    }

    object Network {

        const val serializationJson =
            "org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationJsonVersion"
        const val retrofit2 = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val okhttp3 = "com.squareup.okhttp3:okhttp:$okHttpVersion"
        const val okhttp3LoggingInterceptor =
            "com.squareup.okhttp3:logging-interceptor:$okHttpVersion"
        const val retrofitSerialization =
            "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:$retrofitSerializationVersion"
    }

    object Common {

        const val timber = "com.jakewharton.timber:timber:$timberVersion"
        const val hiltAndroid = "com.google.dagger:hilt-android:$hiltVersion"
        const val hiltCompiler =
            "com.google.dagger:hilt-android-compiler:$hiltAndroidCompilerVersion"
        const val coroutineAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesAndroidVersion"
        const val coroutineCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesCoreVersion"
    }

}