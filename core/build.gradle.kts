buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

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
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
        implementation(project(":utils"))


        val koinVersion = "3.0.2"
        //Koin
        implementation("io.insert-koin:koin-ktor:$koinVersion")
        implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")

        testImplementation(kotlin("test"))
        testImplementation("app.cash.turbine:turbine:0.7.0")
        testImplementation("org.mockito:mockito-inline:3.9.0")
        testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
    }
}
