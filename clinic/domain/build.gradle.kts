dependencies {

    // project
    implementation(project(":common:types"))

    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.31")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.31")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")

    implementation("io.arrow-kt:arrow-core:0.11.0")

    //test
    testImplementation("io.kotest:kotest-runner-junit5:4.4.3")
    testImplementation("io.kotest:kotest-assertions-arrow:4.4.3")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.7.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.7.1")

}