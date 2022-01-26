plugins {
    kotlin("jvm") version "1.6.10"
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "kotlin")

    tasks.test {
        useJUnitPlatform()
    }

    dependencies {
        implementation(project(":utils"))
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    }
}
