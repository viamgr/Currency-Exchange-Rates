plugins {
    `kotlin-dsl`
    `java-library`
}

repositories {
    google()
    mavenCentral()

}
object DependencyVersions {

    const val kotlinVersion = "1.7.0"
    const val androidGradleVersion = "7.3.0"
    const val hiltVersion = "2.43.2"
    const val navVersion = "2.5.1"

}

object GradleClassPath {
    const val kotlinGradlePlugin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${DependencyVersions.kotlinVersion}"
    const val androidGradlePlugin =
        "com.android.tools.build:gradle:${DependencyVersions.androidGradleVersion}"
    const val hiltGradlePlugin =
        "com.google.dagger:hilt-android-gradle-plugin:${DependencyVersions.hiltVersion}"
    const val kotlinSerialization =
        "org.jetbrains.kotlin:kotlin-serialization:${DependencyVersions.kotlinVersion}"
    const val safeArg =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${DependencyVersions.navVersion}"
}

dependencies {
    implementation(GradleClassPath.androidGradlePlugin)
    implementation(GradleClassPath.kotlinGradlePlugin)
    implementation(GradleClassPath.hiltGradlePlugin)
    implementation(GradleClassPath.kotlinSerialization)
    implementation(GradleClassPath.safeArg)
}

project.sourceSets.getByName("main").java.apply {
    srcDir("/build/generated/src")
}
