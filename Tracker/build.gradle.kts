import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    `maven-publish`
}

android {
    namespace = "com.mq.sls.tracker"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.aliyun.log.android)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.gson)

    compileOnly(libs.firebase.analytics.ktx)
    compileOnly(libs.adjust.android)
}

val prop = Properties().apply {
    load(FileInputStream(File(".", "gradle.properties")))
}

publishing {
    repositories {
        mavenLocal()
    }
    publications {
        register<MavenPublication>("release") {
            groupId = "com.mq.sls.tracker"
            version = "sls"
            artifactId = "0.0.1"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}