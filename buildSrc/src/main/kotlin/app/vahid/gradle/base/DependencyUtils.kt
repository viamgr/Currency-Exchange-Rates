package app.vahid.gradle.base

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project


fun DependencyHandler.implementation(vararg modules: Module) {
    modules.forEach {
        add("implementation", project(it.path))
    }
}

fun DependencyHandler.kapt(vararg library: String) {
    library.forEach {
        add("kapt", it)
    }
}

fun DependencyHandler.implementation(vararg library: String) {
    library.forEach {
        add("implementation", it)
    }
}

fun DependencyHandler.api(vararg library: String) {
    library.forEach {
        add("api", it)
    }
}

fun DependencyHandler.api(vararg modules: Module) {
    modules.forEach {
        add("api", project(it.path))
    }
}

fun DependencyHandler.testImplementation(vararg modules: Module) {
    modules.forEach {
        add("testImplementation", project(it.path))
    }
}

fun DependencyHandler.androidTestImplementation(vararg modules: Module) {
    modules.forEach {
        add("androidTestImplementation", project(it.path))
    }
}

fun DependencyHandler.androidTestImplementation(vararg library: String) {
    library.forEach {
        add("androidTestImplementation", it)
    }
}
