

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

allprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
    }
    detekt {
        config.setFrom("$rootDir/config/detekt.yml")
    }
}

koverReport {
    verify {
        // add common verification rule
        rule {
            // check this rule during verification
            isEnabled = true

            // specify the code unit for which coverage will be aggregated
            entity = kotlinx.kover.gradle.plugin.dsl.GroupingEntityType.APPLICATION

            // overriding filters only for current rule
            filters {
                excludes {
                    // excludes class by fully-qualified JVM class name, wildcards '*' and '?' are available
                    classes("com.example.*")
                    // excludes all classes located in specified package and it subpackages, wildcards '*' and '?' are available
                    packages("com.another.subpackage")
                    // excludes all classes and functions, annotated by specified annotations (with BINARY or RUNTIME AnnotationRetention), wildcards '*' and '?' are available
                    annotatedBy("*Generated*")
                }
                includes {
                    // includes class by fully-qualified JVM class name, wildcards '*' and '?' are available
                    classes("com.example.*")
                    // includes all classes located in specified package and it subpackages
                    packages("com.another.subpackage")
                }
            }

            // specify verification bound for this rule
            bound {
                // lower bound
                minValue = 70

                // upper bound
                maxValue = 99

                // specify which units to measure coverage for
                metric = kotlinx.kover.gradle.plugin.dsl.MetricType.LINE

                // specify an aggregating function to obtain a single value that will be checked against the lower and upper boundaries
                aggregation = kotlinx.kover.gradle.plugin.dsl.AggregationType.COVERED_PERCENTAGE
            }

            // add lower bound for percentage of covered lines
            minBound(70)

            // add upper bound for percentage of covered lines
            maxBound(98)
        }
    }
    defaults {
        html {
            onCheck = false
        }
        xml {
            onCheck = false
            // overriding filters only for the XML report
            filters {
                // exclusions for XML reports
                excludes {
                    // excludes class by fully-qualified JVM class name, wildcards '*' and '?' are available
                    classes("com.example.*")
                    // excludes all classes located in specified package and it subpackages, wildcards '*' and '?' are available
                    packages("com.another.subpackage")
                    // excludes all classes and functions, annotated by specified annotations (with BINARY or RUNTIME AnnotationRetention), wildcards '*' and '?' are available
                    annotatedBy("*Generated*")
                }

                // inclusions for XML reports
                includes {
                    // includes class by fully-qualified JVM class name, wildcards '*' and '?' are available
                    classes("com.victorhvs.*")
                    // includes all classes located in specified package and it subpackages
                    packages("com.victorhvs.*")
                }
            }
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
