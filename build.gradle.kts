plugins {
	alias(libs.plugins.loom)
    java
}

val lwjglNatives = resolveLwjglNatives()

val modVersion = "${providers.gradleProperty("mod_version").get()}+${libs.versions.bta.get()}"
val modGroup: Provider<String> = providers.gradleProperty("mod_group")
val modName: Provider<String> = providers.gradleProperty("mod_name")

val javaVersion: Provider<Int> = libs.versions.java.map { it.toInt() }

base.archivesName = modName
group = modGroup.get()
version = modVersion
loom {
	val btaChannel = libs.versions.btaChannel.get()
	val btaVersion = (if (btaChannel == "nightly") "" else "v") + libs.versions.bta.get()
    customMinecraftMetadata.set("https://downloads.betterthanadventure.net/bta-client/${btaChannel}/$btaVersion/manifest.json")
}
repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/") { name = "Fabric" }
    maven("https://maven.thesignalumproject.net/infrastructure") { name = "SignalumMavenInfrastructure" }
    maven("https://maven.thesignalumproject.net/releases") { name = "SignalumMavenReleases" }
	maven("https://maven.thesignalumproject.net/nightly") { name = "SignalumMavenNightly" }
    ivy("https://piston-data.mojang.com") {
        patternLayout { artifact("v1/[organisation]/[revision]/[module].jar") }
        metadataSources { artifact() }
    }
}
dependencies {
    minecraft("::${libs.versions.bta.get()}")

	// Required at compilation & runtime
	// included in builds as a runtime dependency
	implementation(libs.loader)
	implementation(libs.halplibe) // If you do not need halplibe you can delete this line

	// Only required at compilation
	// provides documentation, can be removed if that isn't needed
	compileOnly(libs.bundles.btaLwjgl)
	compileOnly(libs.joml)
	compileOnly(libs.joml.primitives)
	compileOnly(libs.slf4jApi)

	// Only required for development/launch at runtime, won't be part of any builds
	localRuntime(libs.modMenu) // Optional, can be removed
	runtimeClasspath(libs.clientJar)
	val lwjglVer = libs.versions.lwjgl.get()
	localRuntime(platform("org.lwjgl:lwjgl-bom:${lwjglVer}"))
	localRuntime("org.lwjgl:lwjgl::$lwjglNatives")
	localRuntime("org.lwjgl:lwjgl-glfw::$lwjglNatives")
	localRuntime("org.lwjgl:lwjgl-openal::$lwjglNatives")
	localRuntime("org.lwjgl:lwjgl-opengl::$lwjglNatives")
	localRuntime("org.lwjgl:lwjgl-stb::$lwjglNatives")
}
java {
	toolchain {
		languageVersion = javaVersion.map { JavaLanguageVersion.of(it) }
		vendor = JvmVendorSpec.ADOPTIUM
	}
	sourceCompatibility = JavaVersion.toVersion(javaVersion.get())
	targetCompatibility = JavaVersion.toVersion(javaVersion.get())
	withSourcesJar()
}
val licenseFile = run {
	val rootLicense = layout.projectDirectory.file("LICENSE")
	val parentLicense = layout.projectDirectory.file("../LICENSE")
	when {
		rootLicense.asFile.exists() -> {
			logger.lifecycle("Using LICENSE from project root: {}", rootLicense.asFile)
			rootLicense
		}
		parentLicense.asFile.exists() -> {
			logger.lifecycle("Using LICENSE from parent directory: {}", parentLicense.asFile)
			parentLicense
		}
		else -> {
			logger.warn("No LICENSE file found in project or parent directory.")
			null
		}
	}
}
tasks {
	withType<JavaCompile>().configureEach {
		options.encoding = "UTF-8"
		sourceCompatibility = javaVersion.get().toString()
		targetCompatibility = javaVersion.get().toString()
		if (javaVersion.get() > 8) options.release = javaVersion
	}
	named<UpdateDaemonJvm>("updateDaemonJvm") {
		languageVersion = libs.versions.gradleJava.map { JavaLanguageVersion.of(it.toInt()) }
		vendor = JvmVendorSpec.ADOPTIUM
	}
	withType<JavaExec>().configureEach { defaultCharacterEncoding = "UTF-8" }
	withType<Javadoc>().configureEach { options.encoding = "UTF-8" }
	withType<Test>().configureEach { defaultCharacterEncoding = "UTF-8" }
	withType<Jar>().configureEach {
		licenseFile?.let {
			from(it) {
				rename { original -> "${original}_${archiveBaseName.get()}" }
			}
		}
	}
	processResources {
		val resourceMap = mapOf(
			"version" to modVersion,
			"fabricloader" to libs.versions.loader.get(),
			"halplibe" to libs.versions.halplibe.get(),
			"java" to libs.versions.java.get(),
			"modmenu" to libs.versions.modMenu.get()
		)
		// This is needed for gradle to recognize changes
		// made to expanded files
		inputs.properties(resourceMap)

		duplicatesStrategy = DuplicatesStrategy.INCLUDE
		with(copySpec {
			from("src/main/resources/") {
				include("fabric.mod.json")
				include("*.mixins.json")
				expand(resourceMap)
			}
		})
	}
}
// Removes all outdated manifest.json dependencies
configurations.configureEach {
	exclude(group = "org.lwjgl.lwjgl")
	exclude(group = "net.java.jutils")
	exclude(group = "net.java.jinput")
	exclude(group = "net.sf.jopt-simple")
	exclude(group = "net.minecraft", module = "launchwrapper")
}

fun resolveLwjglNatives(): String { // Sourced from https://www.lwjgl.org/
	return Pair(
		System.getProperty("os.name")!!,
		System.getProperty("os.arch")!!
	).let { (name, arch) ->
		when {
			arrayOf("Linux", "SunOS", "Unit").any { name.startsWith(it) } ->
				if (arrayOf("arm", "aarch64").any { arch.startsWith(it) })
					"natives-linux${if (arch.contains("64") || arch.startsWith("armv8")) "-arm64" else "-arm32"}"
				else
					"natives-linux"
			arrayOf("Mac OS X", "Darwin").any { name.startsWith(it) } ->
				"natives-macos${if (arch.startsWith("aarch64")) "-arm64" else ""}"
			arrayOf("Windows").any { name.startsWith(it) } ->
				if (arch.contains("64"))
					"natives-windows${if (arch.startsWith("aarch64")) "-arm64" else ""}"
				else
					"natives-windows-x86"
			else ->
				throw Error("Unrecognized or unsupported platform. Please set \"lwjglNatives\" manually")
		}
	}
}
