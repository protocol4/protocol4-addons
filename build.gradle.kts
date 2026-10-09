import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	id("net.fabricmc.fabric-loom")
	id("org.jetbrains.kotlin.jvm") version "2.4.20"
	id("com.gradleup.shadow") version "9.6.0"
}

repositories {
	mavenCentral()
	maven("https://maven.notenoughupdates.org/releases") {
		content {
			includeGroupAndSubgroups("org.notenoughupdates")
		}
	}
}

val shadowImpl = configurations.create("shadowImpl")
configurations.implementation.get().extendsFrom(shadowImpl)

fabricApi {
	configureDataGeneration {
		client = true
		modId = "protocol4-addons"
	}
}

dependencies {
	minecraft("com.mojang:minecraft:${providers.gradleProperty("minecraft_version").get()}")
	implementation("net.fabricmc:fabric-loader:${providers.gradleProperty("loader_version").get()}")
	implementation("net.fabricmc.fabric-api:fabric-api:${providers.gradleProperty("fabric_api_version").get()}")
	implementation("net.fabricmc:fabric-language-kotlin:${providers.gradleProperty("fabric_kotlin_version").get()}")

	shadowImpl("org.notenoughupdates.moulconfig:modern-${providers.gradleProperty("minecraft_version").get()}:${providers.gradleProperty("moulconfig_version").get()}") {
		exclude("org.jetbrains.kotlin")
		exclude("org.jetbrains.kotlinx")
	}
}

tasks.processResources {
	val version = version
	inputs.property("version", version)

	filesMatching("fabric.mod.json") {
		expand("version" to version)
	}
}

tasks.withType<JavaCompile>().configureEach {
	options.release = 25
}

kotlin {
	compilerOptions {
		jvmTarget = JvmTarget.JVM_25
	}
}

java {
	withSourcesJar()
	sourceCompatibility = JavaVersion.VERSION_25
	targetCompatibility = JavaVersion.VERSION_25
}

tasks.jar {
	val projectName = project.name
	inputs.property("projectName", projectName)

	from("LICENSE") {
		rename { "${it}_$projectName" }
	}
}

tasks.jar {
	archiveClassifier.set("nodeps")
}

tasks.shadowJar {
	archiveClassifier.set("")
	configurations = listOf(shadowImpl)
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
	exclude("META-INF/versions/**")
	exclude("META-INF/*.kotlin_module")
	mergeServiceFiles()
	relocate("io.github.notenoughupdates.moulconfig", "io.github.protocol4.deps.moulconfig")
}

tasks.assemble {
	dependsOn(tasks.shadowJar)
}
