pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "VahidMoradiChallenge"
include(":app")
include(":common:test_shared")
include(":common:core")
include(":common:presentation")
include(":common:domain_common")
include(":base_ui:common")
include(":base_ui:android_test_shared")
include(":base_ui:theme")
include(":feature:currency_exchange:ui")
include(":feature:currency_exchange:presentation")
include(":domain:gateway")
include(":domain:use_case")
include(":repository")
include(":datasource:cache")
include(":datasource:remote")