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
            "1.16.5", //Java 8
            "1.17.1", //1.17-1.17.1: Java 16
            "1.18.2", //1.18-1.18.2: fabric-command-api-v1(1.18.2-), ClientPlayerInteraction, translatableText
            "1.19.2", //1.19-1.19.2: Registry, SoundEvent, fabric-command-api-v2(1.19+)
            //"1.20.1", //LTS
            "1.20.4", //1.19.3-1.20.4: Java 17(1.20.4-), OnUse
            //"1.21.1", //LTS
            "1.21.4", //1.20.5-1.21.4: Java 21(1.20.5+), playSound:PlayerEntity(1.21.4-)->Entity(1.21.5+)
            "1.21.8", //1.21.5-1.21.8: Player.getWorld()
            "1.21.10" //1.21.9-latest
        )
        vcsVersion = "1.21.8"
    }
}