import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	kotlin("jvm")
	id("net.fabricmc.fabric-loom")
	id("com.gradleup.shadow")
	id("versioned-catalogues")
}

repositories {
	mavenCentral()
	maven("https://maven.notenoughupdates.org/releases") {
		content {
			includeGroupAndSubgroups("org.notenoughupdates")
		}
	}
	mavenLocal {
		content {
			includeGroupAndSubgroups("org.notenoughupdates")
		}
	}
}

tasks.withType<JavaCompile>().configureEach {
	options.encoding = "UTF-8"
	options.release.set(25)
}

kotlin {
	jvmToolchain(25)
	compilerOptions {
		jvmTarget = JvmTarget.JVM_25
	}
}

java {
	toolchain.languageVersion = JavaLanguageVersion.of(25)
	withSourcesJar()
}

val mcVersion = stonecutter.current.version.replace(".", "")

loom {
	runConfigs["client"].apply {
		generateRunConfig = true
		runDirectory = project.file("../../run")
		jvmArguments.addAll("-Dfabric.modsFolder=${mcVersion}Mods")
	}
}

fabricApi {
	configureDataGeneration {
		client = true
		modId = "protocol4-addons"
	}
}

val shadowImpl = configurations.create("shadowImpl")
configurations.implementation.get().extendsFrom(shadowImpl)

dependencies {
	"minecraft"(versionedCatalog["minecraft"])
	implementation(versionedCatalog["fabric.loader"])
	implementation(versionedCatalog["fabric.api"])
	implementation(versionedCatalog["fabric.language.kotlin"])

	val moulConfig = versionedCatalog["moulconfig"].get()
	shadowImpl("${moulConfig.module}:${moulConfig.versionConstraint.requiredVersion}") {
		exclude(group = "org.jetbrains.kotlin")
		exclude(group = "org.jetbrains.kotlinx")
	}
}

tasks.processResources {
	val replacements = mapOf(
		"version" to version,
		"minecraft_range" to versionedCatalog.versions["minecraft.range"].requiredVersion,
	)
	inputs.properties(replacements)

	filesMatching("fabric.mod.json") {
		expand(replacements)
	}
}

val archiveName = "protocol4-addons"

base {
	archivesName.set("$archiveName-${archivesName.get()}")
}

val licenseFile = rootProject.file("LICENSE")
val licenseSuffix = rootProject.name

tasks.jar {
	archiveClassifier.set("nodeps")

	from(licenseFile) {
		rename { "${it}_$licenseSuffix" }
	}
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

//smart stuff
val builtJar = layout.buildDirectory.file("libs/$archiveName-${project.name}-$version.jar")
val collectedJar = rootProject.layout.projectDirectory.file("build/libs/$archiveName-$version-${project.name}.jar")

tasks.build {
	doLast {
		val target = collectedJar.asFile
		target.parentFile.mkdirs()
		builtJar.get().asFile.copyTo(target, overwrite = true)
	}
}
