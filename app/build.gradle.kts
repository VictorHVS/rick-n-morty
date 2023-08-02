@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinx.serialization)
    kotlin("kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.victorhvs.rnm"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.victorhvs.rnm"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"

        if (project.findProperty("enableComposeCompilerReports") == "true") {
            val outputDir = project.buildDir.path + "/compose-reports"
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-P",
                "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=$outputDir",
                "-P",
                "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=$outputDir",
            )
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    testOptions {
        animationsDisabled = true
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)

    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.landscapist.glide)
    implementation(libs.hilt.android)
    implementation(libs.kotlinx.serialization.json)
    kapt(libs.hilt.compiler)

    implementation(libs.retrofit)
    implementation(libs.retrofit.serialization)
    implementation(libs.retrofit.logging.interceptor)

    testImplementation(libs.junit)
    testImplementation(libs.ui.test.junit4)
    testImplementation(libs.ui.test.manifest)
    testImplementation(libs.robolectric)
    testImplementation(libs.androidx.test.ext.junit)
    testImplementation(libs.espresso.core)
    testImplementation(platform(libs.compose.bom))

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)

    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}
