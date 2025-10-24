pluginManagement {
    repositories {
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        mavenCentral()
        gradlePluginPortal()

        //StoneCutter
        maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.7.10"
}

stonecutter {
    create(rootProject) {
        versions(
            "1.16.5",
            "1.17.1",
            "1.18.2",
            "1.19.2",
            "1.20.1",
            "1.20.4",
            "1.20.6",
            "1.21.1",
            "1.21.3",
            "1.21.4",
            "1.21.5",
            "1.21.8",
            "1.21.10"
        )
        vcsVersion = "1.21.10"
    }
}