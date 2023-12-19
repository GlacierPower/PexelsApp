
buildscript {
    dependencies {

        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.43.2")
        classpath ("com.google.gms:google-services:4.3.10")


    }
}

plugins {
    id ("com.android.application") version "8.2.0" apply false
    id ("com.android.library") version "8.2.0" apply false
    id ("org.jetbrains.kotlin.android") version "1.8.10" apply false
}