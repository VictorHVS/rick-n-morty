// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kover)
    alias(libs.plugins.sonar)
}

koverReport {
    defaults {
        xml {
            onCheck = true
        }
    }
}

sonar {
    properties {
        property("sonar.projectKey", "VictorHVS_rick-n-morty")
        property("sonar.organization", "victorhvs")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.pullrequest.provider", "GitHub")
        property("sonar.pullrequest.github.repository", "VictorHVS/rick-n-morty")
    }
}

true // Needed to make the Suppress annotation work for the plugins block