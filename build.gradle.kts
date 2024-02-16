buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")
    }
}

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation("org.pushing-pixels:radiance-animation:6.5.0")
    implementation("org.pushing-pixels:radiance-animation-ktx:6.5.0")
}
