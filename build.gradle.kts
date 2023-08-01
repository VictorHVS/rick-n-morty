

// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kover)
    alias(libs.plugins.sonar)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
}

subprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
        plugin("org.jetbrains.kotlinx.kover")
    }
    detekt {
        config.setFrom("$rootDir/config/detekt.yml")
    }
    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        include("**/src/main/java/**") // only analyze a sub package inside src/main/kotlin
        exclude("**/theme/**") // but exclude our legacy internal package
    }

    sonarqube {
        properties {
            property("sonar.sources", "src/main/java")
            property("sonar.tests", "src/test/java")
            property(
                "sonar.coverage.jacoco.xmlReportPaths",
                listOf(
                    "$buildDir/reports/jacoco/testCodeCoverageReport/testCodeCoverageReport.xml",
                    "$buildDir/reports/jacoco/testCodeCoverageReport/jacocoTestReport.xml",
                    "$buildDir/reports/jacoco/test/jacocoTestReport.xml",
                    "$buildDir/reports/kover/xml/coverage.xml",
                    "$buildDir/reports/kover/reportDebug.xml"
                )
            )
        }
    }

    koverReport {
        filters {
            excludes {
                annotatedBy("*Preview*")
                packages("com.victorhvs.rnm.presentation.theme")
                classes("*MainActivity*")
            }
        }
    }
}

sonarqube {
    properties {
        property("sonar.projectKey", "VictorHVS_rick-n-morty")
        property("sonar.organization", "victorhvs")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.pullrequest.provider", "GitHub")
        property("sonar.pullrequest.github.repository", "VictorHVS/rick-n-morty")
        property("sonar.junit.reportsPath", "build/reports/")
        property("sonar.java.coveragePlugin", "jacoco")
        property("sonar.coverage.jacoco.xmlReportPaths", "$buildDir/reports/kover/reportDebug.xml")
        property("sonar.jacoco.reportPath", "build/jacoco/test.exec")
        property("sonar.java.coveragePlugin", "jacoco")
        property("sonar.language", "kotlin")
        property("sonar.log.level", "TRACE")
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.tags", "android")
        property("sonar.verbose", true)
        property("sonar.exclusions", "**/theme/**, **/MainActivity.kt")
    }
}

true // Needed to make the Suppress annotation work for the plugins block
