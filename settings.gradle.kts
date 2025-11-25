val modName = providers.gradleProperty("mod_name")
rootProject.name = modName.get()
pluginManagement {
	repositories {
		gradlePluginPortal()
		maven("https://maven.fabricmc.net/") { name = "Fabric" }
		maven("https://jitpack.io") { name = "Jitpack" }
		maven("https://maven.thesignalumproject.net/infrastructure") { name = "SignalumMavenInfrastructure" }
	}
	val foojayResolverVersion = providers.gradleProperty("foojay_resolver_version")
	val loomVersion = providers.gradleProperty("loom_version")
	plugins {
		id("org.gradle.toolchains.foojay-resolver-convention").version(foojayResolverVersion.get())
		id("fabric-loom").version(loomVersion.get())
	}
}
plugins {
	id("org.gradle.toolchains.foojay-resolver-convention")
}
