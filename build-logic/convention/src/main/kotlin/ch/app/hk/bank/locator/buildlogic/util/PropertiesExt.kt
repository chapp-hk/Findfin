package ch.app.hk.bank.locator.buildlogic.util

import org.gradle.api.Project
import org.jetbrains.kotlin.konan.properties.Properties

/**
 * Retrieves a property value from a properties file.
 *
 * @param fileName The name of the properties file. Defaults to "local.properties".
 * @param key The key of the property to retrieve.
 * @param defaultValue The default value to return if the property is not found.
 * @return The value of the property, or the default value if the property is not found.
 */
fun Project.getLocalProperties(
    fileName: String = "local.properties",
    key: String,
    defaultValue: String,
): String {
    val properties = Properties().apply { load(project.rootProject.file(fileName).inputStream()) }
    return properties.getProperty(key, defaultValue)
}
