// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("com.android.library") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" apply false
    id("com.google.dagger.hilt.android") version "2.50" apply false
    id("com.google.devtools.ksp") version "2.0.0-1.0.22" apply false
}

buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.2")
        classpath("com.google.firebase:firebase-crashlytics-gradle:3.0.2")
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

fun credentialData(): java.util.Properties {
    val credentialProperties = java.util.Properties()
    credentialProperties.load(java.io.FileInputStream(project.rootProject.file("credential.properties")))
    return credentialProperties
}

val credentialProperties = credentialData()
ext {
    // KEYSTORE CONFIG
    set("credentialStoreFile", credentialProperties["storeFile"].toString())
    set("credentialStorePassword", credentialProperties["storePassword"].toString())
    set("credentialKeyAlias", credentialProperties["keyAlias"].toString())
    set("credentialKeyPassword", credentialProperties["keyPassword"].toString())
}