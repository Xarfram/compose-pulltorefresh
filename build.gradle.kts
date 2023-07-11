buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        classpath(libs.gradlePlugin.mavenPublish)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }

    group = property("GROUP") as String
    version = property("VERSION_NAME") as String
}
