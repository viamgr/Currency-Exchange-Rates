import app.vahid.gradle.base.configureAndroid
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