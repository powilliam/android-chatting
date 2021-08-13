package com.powilliam.android.buildsrc

object Dependencies {
    const val kotlinVersion = "1.5.21"
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.0.0"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    const val googleServicesGradlePlugin = "com.google.gms:google-services:4.3.8"

    object Coil {
        const val coilCompose = "io.coil-kt:coil-compose:1.3.2"
    }

    object Kotlin {
        const val kotlinDateTime = "org.jetbrains.kotlinx:kotlinx-datetime:0.2.1"
    }

    object Google {
        const val materialDesign = "com.google.android.material:material:1.4.0"
        const val playServicesAuth = "com.google.android.gms:play-services-auth:19.2.0"

        object Firebase {
            const val bom = "com.google.firebase:firebase-bom:28.3.1"
            const val auth = "com.google.firebase:firebase-auth-ktx"
            const val realtimeDatabase = "com.google.firebase:firebase-database-ktx"
            const val crashlyticsGradlePlugin = "com.google.firebase:firebase-crashlytics-gradle:2.7.1"
            const val crashlytics = "'com.google.firebase:firebase-crashlytics-ktx"
            const val analytics = "com.google.firebase:firebase-analytics-ktx"
        }
    }

    object AndroidX {
        const val core = "androidx.core:core-ktx:1.6.0"
        const val appCompat = "androidx.appcompat:appcompat:1.3.1"
        const val constraintLayoutCompose =
            "androidx.constraintlayout:constraintlayout-compose:1.0.0-beta02"

        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.3.1"
        }

        object Compose {
            const val composeVersion = "1.0.1"
            const val ui = "androidx.compose.ui:ui:$composeVersion"
            const val foundation = "androidx.compose.foundation:foundation:$composeVersion"
            const val animation = "androidx.compose.animation:animation:$composeVersion"
            const val tooling = "androidx.compose.ui:ui-tooling:$composeVersion"

            const val materialDesign = "androidx.compose.material:material:$composeVersion"
            const val materialDesignIcons =
                "androidx.compose.material:material-icons-core:$composeVersion"
            const val materialDesignIconsExtended =
                "androidx.compose.material:material-icons-extended:$composeVersion"

            const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:$composeVersion"
        }

        object Lifecycle {
            const val lifecycleVersion = "2.4.0-alpha03"

            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
            const val viewModelRuntime =
                "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
            const val viewModelCompose =
                "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07"
            const val viewModelSavedState =
                "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion"

            const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"

            const val lifecycleCompiler =
                "androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion"
        }

        object Navigation {
            const val navigationCompose = "androidx.navigation:navigation-compose:2.4.0-alpha06"
        }
    }

    object Koin {
        const val koinVersion = "3.1.2"

        const val koinCore = "io.insert-koin:koin-core:$koinVersion"
        const val koinAndroid = "io.insert-koin:koin-android:$koinVersion"
        const val koinAndroidCompose = "io.insert-koin:koin-androidx-compose:$koinVersion"
    }
}