import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
}

val localProperties = Properties()

file("../local.properties").takeIf { it.exists() }?.inputStream()?.use {
    localProperties.load(it)
}

android {
    namespace = "com.rezakur.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            buildConfigField("String", "PASS_PHRASE", "\"${localProperties.getProperty("passPhrase", "")}\"")
            buildConfigField("String", "API_KEY", "\"${localProperties.getProperty("apiKey", "")}\"")
            buildConfigField("String", "CERTIFICATE_PIN_1", "\"${localProperties.getProperty("certificatePin1", "")}\"")
            buildConfigField("String", "CERTIFICATE_PIN_2", "\"${localProperties.getProperty("certificatePin2", "")}\"")
            buildConfigField("String", "CERTIFICATE_PIN_3", "\"${localProperties.getProperty("certificatePin3", "")}\"")
        }
        release {
            buildConfigField("String", "PASS_PHRASE", "\"${localProperties.getProperty("passPhrase", "")}\"")
            buildConfigField("String", "API_KEY", "\"${localProperties.getProperty("apiKey", "")}\"")
            buildConfigField("String", "CERTIFICATE_PIN_1", "\"${localProperties.getProperty("certificatePin1", "")}\"")
            buildConfigField("String", "CERTIFICATE_PIN_2", "\"${localProperties.getProperty("certificatePin2", "")}\"")
            buildConfigField("String", "CERTIFICATE_PIN_3", "\"${localProperties.getProperty("certificatePin3", "")}\"")
            isMinifyEnabled = true
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
    }
    viewBinding {
        enable = true
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    // Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    api(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Networking
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // Database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Image
    api(libs.glide)

    // Dependency Injection
    api(libs.koin.core)
    api(libs.koin.android)

    // Security
    implementation(libs.android.database.sqlcipher)
    implementation(libs.androidx.sqlite.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}