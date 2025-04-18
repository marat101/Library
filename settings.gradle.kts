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

include(":core_auth:api")
include(":core_auth:impl")

include(":core_network:api")
include(":core_network:impl")

include(":feature_root:api")
include(":feature_root:impl")

include(":feature_home:api")
include(":feature_home:impl")

include(":feature_profile:api")
include(":feature_profile:impl")

include(":feature_search:api")
include(":feature_search:impl")

include(":feature_settings:api")
include(":feature_settings:impl")
include(":feature_settings:data")

include(":feature_auth:api")
include(":feature_auth:impl")
include(":feature_auth:presentation")

include(":feature_reader:api")
include(":feature_reader:impl")

include(":feature_book:api")
include(":feature_book:impl")