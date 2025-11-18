@file:Suppress("UnstableApiUsage", "PropertyName")

import org.apache.tools.ant.taskdefs.condition.Os

plugins {
    id("fabric-loom")
    java
}

val lwjglVersion = providers.gradleProperty("lwjgl_version")
val lwjglNatives = when {
    Os.isFamily(Os.FAMILY_UNIX) && !Os.isFamily(Os.FAMILY_MAC) -> "natives-linux"
    Os.isFamily(Os.FAMILY_WINDOWS) -> "natives-windows"
    Os.isFamily(Os.FAMILY_MAC) -> "natives-macos${if (Os.isArch("aarch64")) "-arm64" else ""}"
    else -> error("Unsupported OS")
}

val modVersion = providers.gradleProperty("mod_version")
val modGroup = providers.gradleProperty("mod_group")
val modName = providers.gradleProperty("mod_name")

val btaChannel = providers.gradleProperty("bta_channel")
val btaVersion = providers.gradleProperty("bta_version")

val loaderVersion = providers.gradleProperty("loader_version")
val legacyLwjglVersion = providers.gradleProperty("legacy_lwjgl_version")

val halplibeVersion = providers.gradleProperty("halplibe_version")
val modMenuVersion = providers.gradleProperty("mod_menu_version")

val slf4jApiVersion = providers.gradleProperty("slf4j_api_version")
val log4jVersion = providers.gradleProperty("log4j_version")
val guavaVersion = providers.gradleProperty("guava_version")
val gsonVersion = providers.gradleProperty("gson_version")
val commonsLang3Version = providers.gradleProperty("commons_lang3_version")

val javaVersion = providers.gradleProperty("java_version")
val gradleJavaVersion = providers.gradleProperty("gradle_java_version")

group = modGroup.get()
base.archivesName = modName.get()
version = modVersion.get()

loom {
    noIntermediateMappings()
    customMinecraftMetadata.set("https://downloads.betterthanadventure.net/bta-client/${btaChannel.get()}/v${btaVersion.get()}/manifest.json")
}

repositories {
    mavenCentral()
	maven("https://jitpack.io")
    maven("https://maven.glass-launcher.net/babric") { name = "Babric" }
    maven("https://maven.fabricmc.net/") { name = "Fabric" }
    maven("https://maven.thesignalumproject.net/infrastructure") { name = "SignalumMavenInfrastructure" }
    maven("https://maven.thesignalumproject.net/releases") { name = "SignalumMavenReleases" }
    ivy("https://github.com/Better-than-Adventure") {
        patternLayout { artifact("[organisation]/releases/download/v[revision]/[module].jar") }
        metadataSources { artifact() }
    }
    ivy("https://downloads.betterthanadventure.net/bta-client/${btaChannel.get()}/") {
        patternLayout { artifact("/v[revision]/client.jar") }
        metadataSources { artifact() }
    }
    ivy("https://downloads.betterthanadventure.net/bta-server/${btaChannel.get()}/") {
        patternLayout { artifact("/v[revision]/server.jar") }
        metadataSources { artifact() }
    }
    ivy("https://piston-data.mojang.com") {
        patternLayout { artifact("v1/[organisation]/[revision]/[module].jar") }
        metadataSources { artifact() }
    }
}

dependencies {
    minecraft("::${btaVersion.get()}")
    mappings(loom.layered {})

	// https://piston-data.mojang.com/v1/objects/43db9b498cb67058d2e12d394e6507722e71bb45/client.jar
    modRuntimeOnly("objects:client:43db9b498cb67058d2e12d394e6507722e71bb45")
    // If you do not need Halplibe you can comment out or delete this line.
    modImplementation("turniplabs:halplibe:${halplibeVersion.get()}")
	modImplementation("turniplabs:modmenu-bta:${modMenuVersion.get()}")
	modImplementation("net.fabricmc:fabric-loader:${loaderVersion.get()}")
	modImplementation("com.github.Better-than-Adventure:legacy-lwjgl3:${legacyLwjglVersion.get()}")

	implementation(platform("org.lwjgl:lwjgl-bom:${lwjglVersion.get()}"))
	implementation("org.slf4j:slf4j-api:${slf4jApiVersion.get()}")

	implementation("com.google.guava:guava:${guavaVersion.get()}")
	implementation("com.google.code.gson:gson:${gsonVersion.get()}")

	implementation("org.apache.logging.log4j:log4j-slf4j2-impl:${log4jVersion.get()}")
	implementation("org.apache.logging.log4j:log4j-core:${log4jVersion.get()}")
	implementation("org.apache.logging.log4j:log4j-api:${log4jVersion.get()}")
	implementation("org.apache.logging.log4j:log4j-1.2-api:${log4jVersion.get()}")

	implementation("org.apache.commons:commons-lang3:${commonsLang3Version.get()}")
	include("org.apache.commons:commons-lang3:${commonsLang3Version.get()}")

	implementation("org.lwjgl:lwjgl:${lwjglVersion.get()}")
	implementation("org.lwjgl:lwjgl-assimp:${lwjglVersion.get()}")
	implementation("org.lwjgl:lwjgl-glfw:${lwjglVersion.get()}")
	implementation("org.lwjgl:lwjgl-openal:${lwjglVersion.get()}")
	implementation("org.lwjgl:lwjgl-opengl:${lwjglVersion.get()}")
	implementation("org.lwjgl:lwjgl-stb:${lwjglVersion.get()}")

	runtimeOnly("org.lwjgl:lwjgl::$lwjglNatives")
	runtimeOnly("org.lwjgl:lwjgl-assimp::$lwjglNatives")
	runtimeOnly("org.lwjgl:lwjgl-glfw::$lwjglNatives")
	runtimeOnly("org.lwjgl:lwjgl-openal::$lwjglNatives")
	runtimeOnly("org.lwjgl:lwjgl-opengl::$lwjglNatives")
	runtimeOnly("org.lwjgl:lwjgl-stb::$lwjglNatives")
}

tasks {
	withType<JavaCompile>().configureEach {
		options.encoding = "UTF-8"
		sourceCompatibility = javaVersion.get()
		targetCompatibility = javaVersion.get()
		if (javaVersion.get().toInt() > 8) options.release = javaVersion.get().toInt()
	}
	named<UpdateDaemonJvm>("updateDaemonJvm") {
		languageVersion = JavaLanguageVersion.of(gradleJavaVersion.get().toInt())
		vendor = JvmVendorSpec.ADOPTIUM
	}
	withType<JavaExec>().configureEach { defaultCharacterEncoding = "UTF-8" }
	withType<Javadoc>().configureEach { options.encoding = "UTF-8" }
	withType<Test>().configureEach { defaultCharacterEncoding = "UTF-8" }
	named<Jar>("jar") {
		val rootLicense = layout.projectDirectory.file("LICENSE")
		val parentLicense = layout.projectDirectory.file("../LICENSE")
		val licenseFile = when {
			rootLicense.asFile.exists() -> {
				logger.lifecycle("Using LICENSE from project root: ${rootLicense.asFile}")
				rootLicense
			}
			parentLicense.asFile.exists() -> {
				logger.lifecycle("Using LICENSE from parent directory: ${parentLicense.asFile}")
				parentLicense
			}
			else -> {
				logger.warn("No LICENSE file found in project or parent directory.")
				null
			}
		}
		licenseFile?.let {
			from(it) {
				rename { original -> "${original}_${archiveBaseName.get()}" }
			}
		}
	}
	processResources {
		val stringModVersion = modVersion.get()
		val stringLoaderVersion = loaderVersion.get()
		val stringJavaVersion = javaVersion.get()
		val stringHalplibeVersion = halplibeVersion.get()
		val stringModMenuVersion = modMenuVersion.get()
		inputs.property("modVersion", stringModVersion)
		inputs.property("loaderVersion", stringLoaderVersion)
		inputs.property("javaVersion", stringJavaVersion)
		inputs.property("HalplibeVersion", stringHalplibeVersion)
		inputs.property("modMenuVersion", stringModMenuVersion)
		filesMatching("fabric.mod.json") {
			expand(
				mapOf(
					"version" to stringModVersion,
					"fabricloader" to stringLoaderVersion,
					"halplibe" to stringHalplibeVersion,
					"java" to stringJavaVersion,
					"modmenu" to stringModMenuVersion
				)
			)
		}
		filesMatching("**/*.mixins.json") { expand(mapOf("java" to stringJavaVersion)) }
	}
	java {
		toolchain {
			languageVersion = JavaLanguageVersion.of(javaVersion.get())
			vendor = JvmVendorSpec.ADOPTIUM
		}
		sourceCompatibility = JavaVersion.toVersion(javaVersion.get().toInt())
		targetCompatibility = JavaVersion.toVersion(javaVersion.get().toInt())
		withSourcesJar()
	}
}
// Removes LWJGL2 dependencies
configurations.configureEach { exclude(group = "org.lwjgl.lwjgl") }
