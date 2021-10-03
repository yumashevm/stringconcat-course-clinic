val parentProjectDir = projectDir

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.31" apply false
    id("io.gitlab.arturbosch.detekt") version "1.18.1"
    id("com.github.ben-manes.versions") version "0.39.0"
    id("org.owasp.dependencycheck") version "6.3.1"
}

repositories {
    mavenCentral()
    mavenLocal()
}

subprojects {
    apply {
        plugin("java")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("io.gitlab.arturbosch.detekt")
        plugin("jacoco")
        plugin("com.github.ben-manes.versions")
        plugin("org.owasp.dependencycheck")
    }

    repositories {
        mavenCentral()
        mavenLocal()
    }

    detekt {
        config = files("$parentProjectDir/detekt/detekt-config.yml")
        buildUponDefaultConfig = true
        input = files("src/main/kotlin", "src/test/kotlin")

        reports {
            html.enabled = true
        }

        dependencies {
            detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.18.1")
        }
    }

    tasks {

        val check = named<DefaultTask>("check")

        val jacocoTestReport = named<JacocoReport>("jacocoTestReport")
        val jacocoTestCoverageVerification = named<JacocoCoverageVerification>("jacocoTestCoverageVerification")
        val dependencyUpdate =
            named<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask>("dependencyUpdates")

        dependencyUpdate {
            revision = "release"
            outputFormatter = "txt"
            checkForGradleUpdate = true
            outputDir = "$buildDir/reports/dependencies"
            reportfileName = "updates"
        }

        dependencyUpdate.configure {

            fun isNonStable(version: String): Boolean {
                val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
                val regex = "^[0-9,.v-]+(-r)?$".toRegex()
                val isStable = stableKeyword || regex.matches(version)
                return isStable.not()
            }

            rejectVersionIf {
                isNonStable(candidate.version) && !isNonStable(currentVersion)
            }
        }

        check {
            finalizedBy(jacocoTestReport)
            finalizedBy(dependencyUpdate)
        }

        jacocoTestReport {
            dependsOn(check)
            finalizedBy(jacocoTestCoverageVerification)
        }

        jacocoTestCoverageVerification {
            dependsOn(jacocoTestReport)

            violationRules {

                rule {
                    excludes = listOf("application", "telnet")
                    limit {
                        minimum = BigDecimal("0.94")
                    }
                }
            }
        }

        val failOnWarning = project.properties["allWarningsAsErrors"] != null && project
            .properties["allWarningsAsErrors"] == "true"

        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_11.toString()
                allWarningsAsErrors = failOnWarning
                freeCompilerArgs = listOf("-Xjvm-default=enable")
            }
        }

        withType<JavaCompile> {
            options.compilerArgs.add("-Xlint:all")
        }

        withType<Test> {
            useJUnitPlatform()

            testLogging {
                events(
                    org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
                    org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
                    org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
                )
                showStandardStreams = true
                exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
            }
        }
    }
}