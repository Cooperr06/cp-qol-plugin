plugins {
    `java-library`

    id("com.gradleup.shadow") version "9.6.1"
    id("xyz.jpenilla.run-paper") version "3.1.0"
    id("xyz.jpenilla.resource-factory-paper-convention") version "1.4.0"
}

group = "de.cooperr"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

val springVersion = "7.0.9"
val junitVersion = "6.1.3"
val mockitoVersion = "5.20.0"

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.3.build.+")

    implementation("org.springframework:spring-context:$springVersion")

    testImplementation("org.springframework:spring-test:$springVersion")
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    testImplementation("org.mockito:mockito-junit-jupiter:$mockitoVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

configurations {
    testImplementation {
        extendsFrom(configurations.compileOnly.get())
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

paperPluginYaml {
    main.set("de.cooperr.cpqolplugin.CpQolPlugin")
    apiVersion.set("26.3")
    authors.add("Cooperr06")
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(25)
        options.compilerArgs.add("-parameters")
    }

    shadowJar {
        archiveClassifier.set("")

        duplicatesStrategy = DuplicatesStrategy.INCLUDE

        exclude("META-INF/LICENSE*")
        exclude("META-INF/NOTICE*")
        exclude("META-INF/license*")
        exclude("META-INF/notice*")
        exclude("META-INF/*.kotlin_module")

        mergeServiceFiles()
        append("META-INF/spring.handlers")
        append("META-INF/spring.schemas")
        append("META-INF/spring.tooling")
        append("META-INF/spring/aot.factories")
    }

    assemble {
        dependsOn(shadowJar)
    }

    runServer {
        minecraftVersion("26.3")

        jvmArgs(
            "-Dcom.mojang.eula.agree=true",
            "-Xms2G",
            "-Xmx4G"
        )
    }

    test {
        useJUnitPlatform()
    }
}
