import androidx.navigation.safe.args.generator.ext.joinToCamelCase
import app.vahid.gradle.base.configureAndroid
import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

project.subprojects {
    project.beforeEvaluate {

        plugins.withId(ApplyPlugins.androidLibrary) {
            configureAndroid()
        }
    }

    project.afterEvaluate {
        tasks.withType<JavaCompile>().configureEach {
            sourceCompatibility = JavaVersion.VERSION_11.toString()
            targetCompatibility = JavaVersion.VERSION_11.toString()
        }

        tasks.withType<KotlinCompile> {

            kotlinOptions {
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-Xopt-in=kotlin.RequiresOptIn",
                    "-Xextended-compiler-checks",
                    "-Xinline-classes",
                    "-Xjsr305=strict",
                    "-Xjvm-default=all",
                    "-Xskip-prerelease-check",
                )

                jvmTarget = ConfigData.jvmTarget.toString()
            }

        }

    }
}

allprojects {
    afterEvaluate {

        listOf(
            "stagingTestImplementation",
            "androidTestImplementation",
        ).forEach {
            configurations.findByName(it)?.run {
                exclude(group = "io.mockk", module = "mockk-agent-jvm")
            }
        }

    }
}

val template = """/**
 * Auto generated module configurations.
 */
import app.vahid.gradle.base.Module

sealed class Modules(override val path: String) : Module(path = path) {
    {{MODULE_LIST}}
}
 """

val modulesFile = file("$rootDir/buildSrc/build/generated/src/Modules.kt").also {
    it.ensureParentDirsCreated()
}

val modules = project.subprojects.joinToString("\n    ") {
    val variableName = it.path.split(":").joinToCamelCase()
    "object $variableName : Module(\"${it.path}\")"
}

modulesFile.ensureParentDirsCreated()
modulesFile.writeText(template.replace("{{MODULE_LIST}}", modules))

