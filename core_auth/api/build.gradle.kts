import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "ru.marat.core_auth.api"
    compileSdk = libs.versions.compileSdk.get().toInt()

    lint {
        targetSdk = libs.versions.targetSdk.get().toInt()
    }

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        val localProperties = Properties().apply {
            load(rootProject.file("local.properties").inputStream())
        }
        val apiHost = (localProperties["backend_host"] ?: project.properties["backend_host"])!!.toString()
        val apiPort = (localProperties["backend_port"] ?: project.properties["backend_port"])!!.toString()

        buildConfigField("String", "API_URL", "\"$apiHost:$apiPort\"")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures.buildConfig = true
}

dependencies {
    implementation(libs.ktor.client.core)
}