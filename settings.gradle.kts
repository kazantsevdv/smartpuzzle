dependencyResolutionManagement {
       repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()

    }
}
rootProject.name = "smartpuzzle"
include(":app")
include(":data")
include(":ui-home-screen")
include(":domain")
include(":navigation")
include(":ui-detail-screen")
include(":ui-common")
