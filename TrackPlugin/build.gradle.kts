plugins {
    `kotlin-dsl`
    `maven-publish`
}

java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

kotlin {
    jvmToolchain(11)
}

dependencies {
    compileOnly(libs.android.tools) {
        exclude(group = "org.ow2.asm")
    }
    implementation(libs.jetbrains.kotlin.gradle) {
        exclude(group = "org.ow2.asm")
    }
    implementation(libs.asm)
    implementation(libs.asm.commons)
    implementation(libs.asm.util)
}

publishing {
    repositories {
        mavenLocal()
    }
    publications {
        create<MavenPublication>("release") {
            groupId = ""
        }
    }
}