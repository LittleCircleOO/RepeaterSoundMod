plugins {
    id("fabric-loom") version "1.11-SNAPSHOT"
    id("maven-publish")
}

operator fun Project.get(property: String): String {
    return property(property) as String
}

version = project["mod_version"]
group = project["maven_group"]
var currentVersion = stonecutter.current.version
val targetJavaVersion =
    if(stonecutter.eval(currentVersion, "<=1.16.5")) 8
    else if(stonecutter.eval(currentVersion, "<=1.17.1")) 16
    else if(stonecutter.eval(currentVersion, "<=1.20.4")) 17
    else 21

base {
    archivesName = "${project["archives_base_name"]}_${project["minecraft_version"]}"
}

repositories {
    //ModMenu
    maven("https://maven.terraformersmc.com/releases/")

    //ClothConfig
    maven("https://maven.shedaniel.me/")

    //PlaceholderApi, used by ModMenu
    maven("https://maven.nucleoid.xyz/") { name = "Nucleoid" }
}

dependencies {
    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:${project["minecraft_version"]}")
    mappings("net.fabricmc:yarn:${project["yarn_mappings"]}:v2")
    modImplementation("net.fabricmc:fabric-loader:${project["loader_version"]}")

    // Fabric API. This is technically optional, but you probably want it anyway.
    modImplementation("net.fabricmc.fabric-api:fabric-api:${project["fabric_version"]}")

    modApi("com.terraformersmc:modmenu:${project["modmenu_version"]}")
    modApi("me.shedaniel.cloth:cloth-config-fabric:${project["cloth_config_version"]}")
}

tasks.processResources {
    inputs.property("version", project["version"])

    filesMatching("fabric.mod.json") {
        expand(
            "version" to project["version"],
            "minecraft_compat" to project["minecraft_compat"]
        )
    }

    filesMatching("*.mixins.json") {
        expand(
            "javaVersion" to targetJavaVersion
        )
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release = targetJavaVersion
}

java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    }
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()
}

tasks.jar {
    inputs.property("archivesName", base.archivesName.get())

    from("LICENSE") {
        rename { "${it}_${project["archives_base_name"]}"}
    }
}

// configure the maven publication
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
        // Add repositories to publish to here.
        // Notice: This block does NOT have the same function as the block in the top level.
        // The repositories here will be used for publishing your artifact, not for
        // retrieving dependencies.
    }
}
