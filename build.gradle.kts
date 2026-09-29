plugins {
    id("java-library")
    id("com.gradleup.shadow") version "9.4.3"
    id("xyz.jpenilla.run-paper") version "3.0.2"
}

val pluginDevLib = "com.github.Ampura16:PluginDevLib:devlib_v6-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io") {
        name = "JitPack"
        content {
            includeModule("com.github.Ampura16", "PluginDevLib")
        }
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")

    // IDEA resolves the library from the devlib_v6 branch during Gradle synchronization.
    implementation(pluginDevLib) {
        // Plugin integrations declared by Foundation are supplied by the Paper server when present.
        isTransitive = false
    }

    // PluginDevLib 字节码引用的编译期依赖（见 docs/mcp-ide-devlib-classpath-fix.md）：
    // 运行时这些库由 Paper 服务器提供（lombok 仅编译期使用）。
    compileOnly("org.projectlombok:lombok:1.18.32")
    compileOnly("com.mojang:datafixerupper:4.0.26")
    compileOnly("org.apache.logging.log4j:log4j-core:2.26.1")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

// Trust the cached JitPack SNAPSHOT for 7 days so IDE syncs do not re-hit
// JitPack on every reload. A fresh JitPack on-demand build takes minutes and
// a sync cancelled mid-build leaves the IDE with a stale dependency model
// (missing external library, unresolved imports).
// After pushing new devlib code: run `./gradlew --refresh-dependencies build`
// once in the terminal, then reload the Gradle project in the IDE.
configurations.all {
    resolutionStrategy {
        cacheChangingModulesFor(7, TimeUnit.DAYS)
    }
}

tasks {
    shadowJar {
        archiveClassifier.set("")
        // relocate("org.mineacademy.fo", "top.brmc.devlib")
    }

    jar {
        enabled = false
    }

    build {
        dependsOn(shadowJar)
    }

    runServer {
        // Configure the Minecraft version for our task.
        // This is the only required configuration besides applying the plugin.
        // Your plugin's jar (or shadowJar if present) will be used automatically.
        minecraftVersion("1.21.11")
        jvmArgs("-Xms2G", "-Xmx2G")
    }

    processResources {
        val props = mapOf("version" to version, "description" to project.description)
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}
