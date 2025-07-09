plugins {
    id("java-library")
}

group = "sugar"

java {
    sourceCompatibility = JavaVersion.VERSION_24
    targetCompatibility = JavaVersion.VERSION_24
}

repositories {
    mavenCentral()
}

tasks.register<Jar>("sourcesJar") {
    from(sourceSets.main.get().allSource)
    archiveClassifier.set("sources")
}

artifacts {
    add("archives", tasks.named("sourcesJar"))
}

dependencies {
    compileOnly("org.jetbrains:annotations:26.0.2")
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter("5.12.1")
        }
    }
}