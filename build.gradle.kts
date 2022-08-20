import org.gradle.internal.os.OperatingSystem
import org.jetbrains.kotlin.gradle.plugin.mpp.*

plugins {
	kotlin("multiplatform") version "1.7.10"
	application
}

repositories {
	mavenCentral()
	maven("https://maven.pkg.github.com/Dominaezzz/matrix-kt") {
		credentials {
			username = System.getenv("GITHUB_USER") // Your GitHub username
			password = System.getenv("GITHUB_TOKEN") // A GitHub token with `read:packages`
		}
	}
}

val host: OperatingSystem = OperatingSystem.current()

val kglVersion = "0.1.11"
val lwjglVersion = "3.3.1"
val lwjglNatives = when {
	host.isLinux -> "natives-linux"
	host.isMacOsX -> "natives-macos"
	host.isWindows -> "natives-windows"
	else -> error("Unrecognized or unsupported Operating system. Please set \"lwjglNatives\" manually")
}

application {
	mainClass.set("Main_Kt")
}

kotlin {
	jvm()

	when {
		host.isLinux -> linuxX64("linux")
		host.isMacOsX -> macosX64("macos")
		host.isWindows -> mingwX64("mingw")
		else -> error("Unrecognized or unsupported Operating system.")
	}

	targets.withType<KotlinNativeTarget> {
		binaries {
			executable {
				entryPoint = "main"
			}
		}
	}

	sourceSets {
		named("commonMain") {
			dependencies {
				implementation("com.kgl:kgl-opengl:$kglVersion")
				implementation("com.kgl:kgl-glfw:$kglVersion")
			}
		}

		named("jvmMain") {
			dependencies {
				runtimeOnly("org.lwjgl:lwjgl:$lwjglVersion:$lwjglNatives")
				runtimeOnly("org.lwjgl:lwjgl-glfw:$lwjglVersion:$lwjglNatives")
				runtimeOnly("org.lwjgl:lwjgl-opengl:$lwjglVersion:$lwjglNatives")
			}
		}

		targets.withType<KotlinNativeTarget> {
			get("${name}Main").apply {
				kotlin.srcDir("src/nativeMain/kotlin")
				resources.srcDir("src/nativeMain/resources")

				dependencies {
					implementation("com.kgl:kgl-glfw-static:$kglVersion")
				}
			}
			get("${name}Test").apply {
				kotlin.srcDir("src/nativeTest/kotlin")
				resources.srcDir("src/nativeTest/resources")
			}
		}
	}
}