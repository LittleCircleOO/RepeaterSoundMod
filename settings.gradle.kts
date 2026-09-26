pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/") { name = "Fabric" }
        maven("https://maven.kikugie.dev/releases") { name = "KikuGie Releases" }
        maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.9.8"
    id("dev.kikugie.loom-back-compat") version "0.4.2"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

stonecutter {
    create(rootProject) {
        versions(
            "1.16.5", // 1.16 - 1.16.5: Java 8, command api v1, old Registry & SoundEvent ctor
            "1.18.2", // 1.17 - 1.18.2: useItemOn has ClientLevel param, TextComponent era
            "1.19.2", // 1.19 - 1.19.2: command api v2, Component literals, old Registry & SoundEvent ctor
            "1.20.1", // 1.19.3 - 1.20.4: BuiltInRegistries, createVariableRangeEvent
            "1.20.6", // 1.20.5 - 1.20.6: useWithoutItem (no hand), ResourceLocation ctor
            "1.21.1", // 1.21 - 1.21.1: ResourceLocation.fromNamespaceAndPath
            "1.21.4", // 1.21.2 - 1.21.4: InteractionResult$Fail inner type
            "1.21.10", // 1.21.5 - 1.21.10: playSound(Entity, ...) overload, stable world access via Minecraft.level
            "1.21.11", // 1.21.11: official mappings renamed ResourceLocation -> Identifier
            "26.1.2", // 26.1 - 26.2: unobfuscated, official names, Identifier
            "26.3"    // 26.3: RedstoneWireBlock rename
        )
        vcsVersion = "1.21.10"
    }
}

rootProject.name = "RepeaterSoundMod"
