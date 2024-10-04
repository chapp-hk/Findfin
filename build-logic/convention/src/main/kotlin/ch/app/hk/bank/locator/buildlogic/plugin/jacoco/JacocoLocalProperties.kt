package org.chapp.findfin.buildlogic.plugin.jacoco

import org.chapp.findfin.buildlogic.util.getLocalProperties
import org.gradle.api.Project

/**
 * A data class that holds the local properties related to the Jacoco coverage report configuration.
 *
 * @property buildVariant The build variant for which the coverage report should be generated.
 * @property instrumentTestType The type of instrument test to be used for the coverage report.
 * @property gradleManagedDeviceName The name of the Gradle managed device for which the coverage report should be generated.
 */
data class JacocoLocalProperties(
    val buildVariant: String,
    val instrumentTestType: InstrumentTestType,
    val gradleManagedDeviceName: String,
)

/**
 * An enum that represents the type of instrument test to be used for the Jacoco coverage report.
 */
enum class InstrumentTestType {
    CONNECTED_DEVICE,
    GRADLE_MANAGED_DEVICE;

    companion object {
        /**
         * Converts a string to an `InstrumentTestType` enum value.
         * If the string does not match any enum value, `GRADLE_MANAGED_DEVICE` is returned by default.
         *
         * @param value The string to convert to an `InstrumentTestType` enum value.
         * @return The corresponding `InstrumentTestType` enum value, or `GRADLE_MANAGED_DEVICE` if the string does not match any enum value.
         */
        fun fromString(value: String): InstrumentTestType {
            return runCatching { valueOf(value) }.getOrDefault(GRADLE_MANAGED_DEVICE)
        }
    }
}

/**
 * Retrieves the Jacoco local properties from the local properties file and returns them as an instance of `JacocoLocalProperties`.
 *
 * @return An instance of `JacocoLocalProperties` that holds the local properties related to the Jacoco coverage report configuration.
 */
fun Project.getJacocoLocalProperties(): JacocoLocalProperties {
    val buildVariant = getLocalProperties(
        key = "JACOCO_BUILD_VARIANT",
        defaultValue = "debug",
    )

    val instrumentTestType = getLocalProperties(
        key = "JACOCO_INSTRUMENT_TEST_TYPE",
        defaultValue = "GRADLE_MANAGED_DEVICE",
    )

    val gradleManagedDeviceName = getLocalProperties(
        key = "JACOCO_GRADLE_MANAGED_DEVICE_NAME",
        defaultValue = "pixel8api34",
    )

    return JacocoLocalProperties(
        buildVariant = buildVariant,
        instrumentTestType = InstrumentTestType.fromString(instrumentTestType),
        gradleManagedDeviceName = gradleManagedDeviceName,
    )
}
