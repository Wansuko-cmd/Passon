plugins {
    kotlin("jvm") version "1.6.10"
}

allprojects {

    apply(plugin = "kotlin")

    tasks.test {
        useJUnitPlatform()
    }

    dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")

        testImplementation(kotlin("test"))
        testImplementation("com.google.truth:truth:1.1.3")
    }
}
