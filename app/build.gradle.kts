plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.myapplication1"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.myapplication1"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.activity)
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation(libs.androidx.fragment)
    val fragmentVersion = "1.8.9"
    val material = "1.12.0"
    val nav_version = "2.9.4"
    implementation(libs.androidx.appcompat)
    implementation("com.google.android.material:material:${material}")
    implementation("androidx.fragment:fragment-ktx:${fragmentVersion}")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("androidx.navigation:navigation-fragment-ktx:${nav_version}")
    implementation("androidx.navigation:navigation-ui-ktx:${nav_version}")
    implementation("androidx.transition:transition:1.6.0")
    implementation("com.google.code.gson:gson:2.10.1")
}