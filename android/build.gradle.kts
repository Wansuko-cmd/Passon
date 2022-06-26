// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")

        // safe args
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.0-rc02")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
