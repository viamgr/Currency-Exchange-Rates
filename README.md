# Project Documentation

”Logic will get you from A to B. Imagination will take you everywhere.” - Albert Einstein An

### Summary

This is an Implementation of a native android project with a flexible and maintainable architectural
design using Clean architecture.

### Key Features:

- 100% Kotlin
- Modular Design
- Clean Architecture
- TDD Based

### Design Structure

Here is the list of technologies that used in this project:

1. UI Layer
    - Compose
    - Ui test
    - Material Design
2. Presentation Layer
    - MVI
    - Flow
    - Orbit library
3. Test Env
    - KoTest
    - Mockk
    - Instrumented tests
    - Ui testing
    - Compose testing
4. Base Features
    - Coroutine
    - KTS
    - Hilt
    - Error Handling
5. Data Layer
    - Room
    - Retrofit
    - Pagination

### Project modular relationships diagram:

<img alt="Project Structure" src="doc/project_structure.png" width="600"/>

- **app:** An orchestra module that has access to all modules to organize dependency injection.
- _common_:
    - **test_shared:** Defined utils for unit tests that can be implemented in every needed module
    - **core:** Some utils that can be used on the whole of the app's structure.
    - **presentation:** Base presentation layer
    - **domain_common:** Common domain utilities of the business layer logic.
- _base_ui_:
    - **common:** Common utilities for the UI layers.
    - **android_test_shared:** Defined utils for android tests that can be implemented in every
      android module
    - **theme:** Theme, styles, colors, dimensions, and other theme-related resources.
- _feature_:
    - **currency_exchange:ui:** Implemented Xml/Compose designs and UI controllers.
    - **currency_exchange:presentation:** A non-UI-related middleman to organize UI events and side
      effects with communications with the domain layer and map their results as a view state.
- _domain_:
    - **domain:gateway:** An interface layer for the domain layer to communicate with the repository
      layer.
    - **domain:use_case:** Implementation of business layer logic.
- _repository_: Implementations of the repository layer
- _datasource_:
    - **cache:** Implementation of the local cache persistence.
    - **remote:** API implementation for taking responsibility for how data are coming from by
      exposing some real or mock data module.

### Dependencies :

List of all libraries might use in the project.

- androidx.appcompat:appcompat
- androidx.activity:activity-compose
- androidx.compose.material3:material3
- androidx.compose.material:material
- androidx.constraintlayout:constraintlayout-compose
- org.jetbrains.kotlinx:kotlinx-coroutines-android
- org.jetbrains.kotlinx:kotlinx-coroutines-core
- com.google.dagger:hilt-android
- io.coil-kt:coil-compose
- org.jetbrains.kotlinx:kotlinx-serialization-json
- io.mockk:mockk-agent-jvm
- io.mockk:mockk-android
- io.mockk:mockk-agent-jvm
- io.mockk:mockk
- org.jetbrains.kotlinx:kotlinx-coroutines-test
- io.kotest:kotest-runner-junit5-jvm
- org.jetbrains.kotlin:kotlin-reflect
- app.cash.turbine:turbine
- io.kotest:kotest-assertions-core-jvm
- com.google.dagger:hilt-android-compiler
- com.google.dagger:hilt-android-compiler
- com.google.dagger:hilt-android-testing
- com.google.dagger:hilt-android-testing
- androidx.lifecycle:lifecycle-viewmodel-ktx
- androidx.lifecycle:lifecycle-runtime-ktx
- androidx.compose.ui:ui
- androidx.paging:paging-compose
- androidx.compose.foundation:foundation
- androidx.navigation:navigation-compose
- androidx.hilt:hilt-navigation-compose
- androidx.compose.foundation:foundation-layout
- com.squareup.retrofit2:retrofit
- com.squareup.okhttp3:okhttp
- com.squareup.okhttp3:logging-interceptor
- com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter
- org.orbit-mvi:orbit-viewmodel
- org.orbit-mvi:orbit-compose
- org.orbit-mvi:orbit-test
- com.jakewharton.timber:timber
- androidx.test:runner
- org.junit.jupiter:junit-jupiter-api
- org.jetbrains.kotlin:kotlin-test-junit5
- androidx.test:rules
- androidx.test.ext:junit
- androidx.room:room-runtime
- androidx.room:room-paging
- androidx.room:room-compiler
- androidx.room:room-ktx
- androidx.compose.ui:ui-test-junit4
- androidx.compose.ui:ui-test-manifest