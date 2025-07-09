import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("java-library")
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "sugar"

java {
    sourceCompatibility = JavaVersion.VERSION_24
    targetCompatibility = JavaVersion.VERSION_24
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.19.1")
    compileOnly("org.springframework.boot:spring-boot-starter-web")
    testImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.19.1")
    testImplementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}
