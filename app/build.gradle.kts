plugins {
    alias(libs.plugins.android.application) // Android application plugin
    id("com.google.gms.google-services") // Apply the Google services plugin here for Firebase
}

android {
    namespace = "com.example.quizapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.quizapp"
        minSdk = 24
        targetSdk = 34
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
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("androidx.recyclerview:recyclerview:1.2.1") // Add RecyclerView dependency
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Import Firebase BoM to manage Firebase versions automatically
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))

    // Firebase SDK dependencies
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-auth") // Firebase Authentication
}
