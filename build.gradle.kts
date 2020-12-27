import org.gradle.internal.os.OperatingSystem

plugins {
    kotlin("multiplatform") version "1.4.21"
}

repositories {
    jcenter()
    maven("https://dl.bintray.com/dominaezzz/kotlin-native")
}

val host: OperatingSystem = OperatingSystem.current()

val kglVersion = "0.1.10"
val lwjglVersion = "3.2.2"

val lwjglNatives = when {
    host.isLinux -> "natives-linux"
    host.isMacOsX -> "natives-macos"
    host.isWindows -> "natives-windows"
    else -> error("Unrecognized or unsupported Operating system. Please set \"lwjglNatives\" manually")
}

kotlin {
    jvm()
    mingwX64("mingw") {
        binaries {
            executable("native") {
                entryPoint = "main"
            }
        }
    }

    sourceSets {
        all {
            languageSettings.useExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes")
        }

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

        named("mingwMain") {
            dependencies {
                implementation("com.kgl:kgl-glfw-static:$kglVersion")
            }
        }
    }
}
