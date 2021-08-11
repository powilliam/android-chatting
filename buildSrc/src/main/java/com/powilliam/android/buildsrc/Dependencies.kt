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
            const val materialDesignIcons = "androidx.compose.material:material-icons-core:$composeVersion"
            const val materialDesignIconsExtended = "androidx.compose.material:material-icons-extended:$composeVersion"

            const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:$composeVersion"
        }

        object Lifecycle {
            const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07"
        }
    }
}