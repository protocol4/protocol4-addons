rootProject.name = "protocol4-addons"

pluginManagement {
	repositories {
		gradlePluginPortal()
		mavenCentral()
		maven("https://maven.kikugie.dev/snapshots")
		maven("https://maven.fabricmc.net/")
	}

	plugins {
		id("net.fabricmc.fabric-loom") version "1.18-SNAPSHOT"
		id("org.jetbrains.kotlin.jvm") version "2.4.20"
		id("com.gradleup.shadow") version "9.6.0"
	}
}

plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
	id("dev.kikugie.stonecutter") version "0.10-alpha.12"
}

fun versionParts(version: String) = version.split('.').map(String::toInt)
//this: so i dont have to tweak this file ever again (hopefully)
val versions = rootDir.resolve("gradle").listFiles().orEmpty()
	.mapNotNull { Regex("""(\d+(?:_\d+)+)\.versions\.toml""").matchEntire(it.name)?.groupValues?.get(1) }
	.map { it.replace('_', '.') }
	.sortedWith { a, b ->
		val (pa, pb) = versionParts(a) to versionParts(b)
		(0 until maxOf(pa.size, pb.size))
			.map { (pb.getOrElse(it) { 0 }).compareTo(pa.getOrElse(it) { 0 }) }
			.firstOrNull { it != 0 } ?: 0
	}

require(versions.isNotEmpty()) { "No gradle/<major>_<minor>.versions.toml found" }
gradle.extra["newestMinecraft"] = versions.first()

stonecutter {
	create(rootProject) {
		versions.forEach { version(it) }
		vcsVersion = versions.first()
	}
}

dependencyResolutionManagement {
	versionCatalogs {
		versions.forEach {
			create("libs${it.replace(".", "")}") {
				from(files(rootProject.projectDir.resolve("gradle/${it.replace(".", "_")}.versions.toml")))
			}
		}
	}
}
