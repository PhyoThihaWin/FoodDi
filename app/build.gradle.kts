plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.pthw.food"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pthw.food"
        minSdk = 21
        targetSdk = 34
        versionCode = 8
        versionName = "2.2"
        multiDexEnabled = true
        setProperty("archivesBaseName", "FoodDi-$versionName")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions.add("version")
    productFlavors {
        create("dev") {
            dimension = "version"
            versionNameSuffix = "-dev"
            resValue("string", "app_name", "Dev|FoodDi")
        }
        create("prod") {
            dimension = "version"
            resValue("string", "app_name", "FoodDi")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        viewBinding = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // Added library
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.3")
    implementation("androidx.room:room-runtime:2.6.1") // Room database
    implementation("androidx.room:room-ktx:2.6.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.8")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.github.ybq:Android-SpinKit:1.4.0")
    implementation("com.jakewharton.timber:timber:5.0.1")

    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-storage-ktx")

    // compose
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0-alpha13")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.navigation:navigation-compose:2.8.0-beta05")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.animation:animation:1.7.0-beta05")
    implementation("io.coil-kt:coil-compose:2.6.0")
}