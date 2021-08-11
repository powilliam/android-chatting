package com.powilliam.android.buildsrc

object Dependencies {
    const val kotlinVersion = "1.5.21"
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.0.0"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"

    object Google {
        const val materialDesign = "com.google.android.material:material:1.4.0"
    }

    object AndroidX {
        const val core = "androidx.core:core-ktx:1.6.0"
        const val appCompat = "androidx.appcompat:appcompat:1.3.1"
    }
}