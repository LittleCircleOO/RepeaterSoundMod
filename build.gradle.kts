plugins {
    id("dev.kikugie.loom-back-compat")
}

version = "${property("mod.version")}+${sc.current.version}"
base.archivesName = property("mod.id") as String

val requiredJava: Int = when {
    sc.current.parsed >= "26.1" -> 25
    sc.current.parsed >= "1.20.5" -> 21
    sc.current.parsed >= "1.18" -> 17
    sc.current.parsed >= "1.17" -> 16
    else -> 8
}

repositories {
    // ModMenu
    maven("https://maven.terraformersmc.com/releases/")

    // ClothConfig
    maven("https://maven.shedaniel.me/")

    // PlaceholderApi, used by ModMenu
    maven("https://maven.nucleoid.xyz/") { name = "Nucleoid" }
}

dependencies {
    minecraft("com.mojang:minecraft:${sc.current.version}")
    loomx.applyMojangMappings()
    modImplementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")

    modImplementation(sc.properties["deps.fabric_api"] as String)

    modApi(sc.properties["deps.modmenu"] as String)
    modApi(sc.properties["deps.cloth_config"] as String)
}

java {
    withSourcesJar()
}

val modVersion = property("mod.version") as String
val mcCompat = sc.properties["mod.mc_compat"] as String
val loaderMin = sc.properties["mod.loader_min"] as String
val fabricDep = sc.properties["mod.fabric_dep"] as String

tasks {
    processResources {
        val props = buildMap {
            put("version", "$modVersion+${sc.current.version}")
            put("minecraft", mcCompat)
            put("loader", loaderMin)
            put("fabric_dep", fabricDep)
        }
        inputs.properties(props)

        filesMatching("fabric.mod.json") {
            expand(props)
        }

        val mixinJava = requiredJava
        inputs.property("java", mixinJava)
        filesMatching("*.mixins.json") {
            expand("java" to mixinJava)
        }
    }

    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release = requiredJava
    }

    withType<Jar> {
        from(rootProject.file("LICENSE")) {
            rename { "${it}_RepeaterSound" }
        }
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        description = "Builds mod jars and copies results to `build/libs/{mod version}/`"

        from(loomx.modJar.flatMap { it.archiveFile }, loomx.modSourcesJar.flatMap { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs/$modVersion"))
    }
}
