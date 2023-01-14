@file:Suppress("DSL_SCOPE_VIOLATION", "MISSING_DEPENDENCY_CLASS", "FUNCTION_CALL_EXPECTED", "PropertyName")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    `maven-publish`

    alias(libs.plugins.kotlin)
    alias(libs.plugins.quilt.loom)
}

val archives_base_name: String by project
base.archivesName.set(archives_base_name)

val javaVersion = 17

repositories {

}

dependencies {
    minecraft(libs.minecraft)
    mappings(
        variantOf(libs.quilt.mappings) {
            classifier("intermediary-v2")
        }
    )

    // Mojang mapping
    /*
    mappings(
        loom.layered {
            mappings(variantOf(libs.quilt.mappings) { classifier("intermediary-v2") })
            officialMojangMappings()
        }
    )
    */

    modImplementation(libs.quilt.loader)
    modImplementation(libs.qfapi)
    modImplementation(libs.qkl)
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = javaVersion.toString()
            languageVersion = libs.plugins.kotlin.get().version.requiredVersion.substringBeforeLast('.')
        }
    }

    withType<JavaCompile>.configureEach {
        options.encoding = "UTF-8"
        options.isDeprecation = true
        options.release.set(javaVersion)
    }

    processResources {
        filteringCharset = "UTF-8"
        inputs.property("version", project.version)

        filesMatching("quilt.mod.json") {
            expand(
                mapOf(
                    "version" to project.version
                )
            )
        }
    }

    javadoc {
        options.encoding = "UTF-8"
    }

    wrapper {
        distributionType = Wrapper.DistributionType.BIN
    }

    jar {
        from("LICENSE") {
            rename { "LICENSE_${archives_base_name}" }
        }
    }
}

val targetJavaVersion = JavaVersion.toVersion(javaVersion)
if (JavaVersion.current() < targetJavaVersion) {
    kotlin.jvmToolchain(javaVersion)

    java.toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}

java {
    withSourcesJar()
    // withJavadocJar()

    sourceCompatibility = targetJavaVersion
    targetCompatibility = targetJavaVersion
}


publishing {
    publications {
        register<MavenPublication>("Maven") {
            from(components.getByName("java"))
        }
    }

    repositories {

    }
}
