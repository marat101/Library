pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "Library"
include(":app")

include(":core_navigation")
include(":core_di")
include(":core_ui")

include(":feature_root:api")
include(":feature_root:impl")

include(":feature_home:api")
include(":feature_home:impl")

include(":feature_profile:api")
include(":feature_profile:impl")

include(":feature_search:api")
include(":feature_search:impl")