plugins {
	id("net.fabricmc.fabric-loom") apply false
}

stonecutter active gradle.extra["newestMinecraft"] as String

// ./gradlew buildAll builds every supported version.
tasks.register("buildAll") {
	group = "build"
	dependsOn(provider { subprojects.map { "${it.path}:build" } })
}
