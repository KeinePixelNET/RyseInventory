group = "io.github.rysefoxx.inventory.plugin"
description = "RyseInventory"

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")

    maven("https://oss.sonatype.org/content/repositories/snapshots")

    maven("https://repo.codemc.io/repository/maven-snapshots/")
}

dependencies {
    implementation(project(":v1_19"))
    implementation(project(":v1_18"))
    implementation(project(":v1_17"))
    implementation(project(":v1_16"))
    implementation(project(":api"))
    implementation("net.wesjd:anvilgui:${property("anvil-api")}")

    compileOnly("net.kyori:adventure-platform-bukkit:${property("adventure-bukkit")}")
    compileOnly("org.spigotmc:spigot-api:${property("spigot")}")
    compileOnly("org.apache.commons:commons-lang3:${property("apache-commons")}")
    compileOnly("org.jetbrains:annotations:${property("jetbrains-annotations")}")
    compileOnly("com.google.code.findbugs:jsr305:${property("google-findbugs")}")
    annotationProcessor("org.projectlombok:lombok:${property("lombok")}")
    compileOnly("org.projectlombok:lombok:${property("lombok")}")
}

publishing {
    publications {
        create<MavenPublication>("shadow") {
            groupId = "io.github.rysefoxx.inventory"
            artifactId = "RyseInventory-Plugin"
            version = "${project.version}"

            pom {
                name = "RyseInventory"
                packaging = "jar"
                description = "Inventory System"
                url = "https://github.com/Rysefoxx/RyseInventory"
            }
            project.extensions.configure<com.github.jengelman.gradle.plugins.shadow.ShadowExtension>() {
                component(this@create)
            }
        }
    }
    repositories {
        maven {
            name = "koboo"
            url = uri("https://reposilite.koboo.eu/releases")
            credentials {
                username = System.getenv("REPO_USER")
                password = System.getenv("REPO_TOKEN")
            }
        }
    }
}

tasks {
    shadowJar {
        archiveClassifier.set("")
        relocate("net.wesjd.anvilgui", "io.github.rysefoxx.inventory.anvilgui")
        exclude("io/github/rysefoxx/inventory/plugin/ItemBuilder.class")
    }

    build {
        dependsOn(shadowJar)
    }
}