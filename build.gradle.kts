plugins {
    kotlin("jvm") version embeddedKotlinVersion
    `maven-publish`
}

group = "com.villa.gradle"

/*
How to Publish

1. Bump version parameter
2. Prepare publication credentials for https://kotlin.jetbrains.space/p/kotlin/packages/maven/kotlin-dependencies/org.jetbrains.kotlin/kotlin-build-gradle-plugin
3. Execute `./gradlew -p dependencies/kotlin-build-gradle-plugin publish -PkotlinSpaceUsername=usr -PkotlinSpacePassword=token`
 */
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "23.0"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "23.0"
    }
}

java {
    withSourcesJar()
}

sourceSets {
    main {
        /*TODO: move version to build-plugin*/
        java.setSrcDirs(listOf("src", "../../compiler/util-io/src"))
    }
}

tasks.withType<GenerateModuleMetadata> {
    enabled = false
}

publishing {
    publications {
        create<MavenPublication>("VillaBuildGradlePlugin") {
            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "villaGradlePlugin"
            url = url("https://maven.villa.com/villa-gradle-plugin")
            credentials(org.gradle.api.artifacts.repositories.PasswordCredentials::class)
        }
    }
}

