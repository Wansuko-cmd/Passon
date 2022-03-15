plugins {
    kotlin("jvm") version "1.6.10"
}

allprojects {
    repositories {
        mavenCentral()
    }

    apply(plugin = "kotlin")

    tasks.test {
        useJUnitPlatform()
    }

    dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")

        testImplementation(kotlin("test"))
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
        testImplementation("app.cash.turbine:turbine:0.7.0")
        testImplementation("io.mockk:mockk:1.12.3")
        testImplementation("com.google.truth:truth:1.1.3")
    }
}