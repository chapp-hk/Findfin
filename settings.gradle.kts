pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Findfin"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

// app module
include(":app")

// core modules
include(":core:design:theme")
include(":core:design:ui-foundation")
include(":core:imageloader")
include(":core:locale")
include(":core:location")
include(":core:logging:api")
include(":core:logging:startup")
include(":core:map")
include(":core:navigation")
include(":core:network")
include(":core:preferences:storage")
include(":core:preferences:ui-foundation")
include(":core:threading")

// feature auth modules
include(":feature:auth:data:remote-firebase")
include(":feature:auth:data:repo")
include(":feature:auth:presentation")

// feature bank modules
include(":feature:bank:data:local-database")
include(":feature:bank:data:remote")
include(":feature:bank:data:repo")
include(":feature:bank:navigation")
include(":feature:bank:ui")

// feature onboarding modules
include(":feature:onboarding:domain")
include(":feature:onboarding:presentation")

// feature home modules
include(":feature:home:domain")
include(":feature:home:navigation")
include(":feature:home:ui")

// feature locator modules
include(":feature:locator:data:remote-location")
include(":feature:locator:data:repo")
include(":feature:locator:navigation")
include(":feature:locator:ui")

// feature setting modules
include(":feature:setting:data:local-preferences")
include(":feature:setting:data:repo")
include(":feature:setting:presentation")

// library modules
include(":library:hiltwrap:annotation")
include(":library:hiltwrap:processor-binds")
include(":library:hiltwrap:processor-room")
include(":library:hiltwrap:util")

// lint modules
include(":lint:konsist")

// testing modules
include(":testing:extension")
include(":testing:google-play-services-tasks")
include(":testing:instrument")
include(":testing:network")
include(":testing:util")
