package app.vahid.gradle.base

import org.gradle.api.Project
import java.io.File

fun <T> Project.getLocalProperty(key: String, file: String = "local.properties"): T {
    val properties = java.util.Properties()
    val localProperties = File(file)
    if (localProperties.isFile) {
        java.io.InputStreamReader(java.io.FileInputStream(localProperties), Charsets.UTF_8)
            .use { reader ->
                properties.load(reader)
            }
    } else error("File from not found")

    return properties.getProperty(key) as T
}