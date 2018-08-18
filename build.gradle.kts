import org.gradle.api.file.CopySpec
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import kotlin.collections.set

val mainClass = "XorKt"

plugins {
    kotlin("jvm") version "1.2.60"
    application
}

repositories {
    jcenter()
}

dependencies {
    compile(kotlin("stdlib"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

application {
    mainClassName = mainClass
}

val fatJar = task("fatJar", type = Jar::class) {
    baseName = "${project.name}-fat"
    manifest {
        attributes["Main-Class"] = mainClass
    }
    from(
            configurations.runtime.map {
                if (it.isDirectory) it else zipTree(it)
            }
    )
    with(tasks["jar"] as CopySpec)
}

tasks {
    "build" {
        dependsOn(fatJar)
    }
}