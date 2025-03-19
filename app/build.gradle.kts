plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.daggerHilt)

}

android {
    namespace = "com.example.qrscanner"
    compileSdk = 35


    packaging {
        resources.excludes.add("META-INF/*")
    }

    defaultConfig {
        applicationId = "com.example.qrscanner"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.qrscanner.CustomTestRunner"
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


    buildFeatures{
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.hilt.android)
    implementation(libs.core)
    implementation(libs.camera.camera2)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.view)
    implementation(libs.hilt.common)
    implementation(libs.room.runtime)
    implementation(libs.androidx.recyclerview)
    annotationProcessor(libs.hilt.compiler)
    annotationProcessor(libs.androidx.hilt.compiler)
    annotationProcessor(libs.hilt.work)
    annotationProcessor(libs.room.compiler)
    testAnnotationProcessor(libs.hilt.compiler)
    androidTestAnnotationProcessor (libs.hilt.compiler)
    androidTestImplementation(libs.androidx.espresso.intents)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.androidx.core.testing)
    androidTestImplementation(libs.androidx.rules)
    androidTestImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.androidx.core)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.junit)
    testImplementation(libs.room.testing)


}