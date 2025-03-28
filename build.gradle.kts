plugins {
    alias(libs.plugins.architectury.loom)
    alias(libs.plugins.architectury.plugin)
    alias(libs.plugins.blossom)
}

val minecraft = stonecutter.current.version

version = "${mod.version}+$minecraft"
base {
    archivesName.set("${mod.id}-common")
}

architectury.common(stonecutter.tree.branches.mapNotNull {
    if (stonecutter.current.project !in it) null
    else it.prop("loom.platform")
})

dependencies {
    minecraft("com.mojang:minecraft:$minecraft")
    mappings("net.fabricmc:yarn:$minecraft+build.${mod.dep("yarn_build")}:v2")
    modImplementation(libs.fabric.loader)
}

loom {
    accessWidenerPath = rootProject.file("src/main/resources/template.accesswidener")

    decompilers {
        get("vineflower").apply { // Adds names to lambdas - useful for mixins
            options.put("mark-corresponding-synthetics", "1")
        }
    }
}

blossom {
    replaceToken("{{ MOD_ID }}", mod.id)
    replaceToken("{{ MOD_NAME }}", mod.name)
    replaceToken("{{ MOD_VERSION }}", mod.version)
}

java {
    withSourcesJar()

    val java = if (stonecutter.eval(minecraft, ">=1.20.5")) JavaVersion.VERSION_21
        else if (stonecutter.eval(minecraft, ">=1.18")) JavaVersion.VERSION_17
        else if (stonecutter.eval(minecraft, ">=1.17")) JavaVersion.VERSION_16
        else if (stonecutter.eval(minecraft, ">=1.16")) JavaVersion.VERSION_11
        else JavaVersion.VERSION_1_8
    targetCompatibility = java
    sourceCompatibility = java
}

tasks.build {
    group = "versioned"
    description = "Must run through 'chiseledBuild'"
}
