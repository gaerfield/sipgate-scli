import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion: String by extra
val springBootVersion: String by extra

buildscript {
    val kotlinVersion: String by extra { "1.2.21" }
    val springBootVersion: String by extra { "2.0.0.M2" }
    repositories {
        mavenCentral()
        maven("https://repo.spring.io/milestone")
    }

    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-allopen:$kotlinVersion")
        classpath("org.junit.platform:junit-platform-gradle-plugin:1.0.2")
    }
}

apply {
    plugin("org.springframework.boot")
    plugin("org.junit.platform.gradle.plugin")
    plugin("kotlin-spring")
}

plugins {
    val kotlinVersion = "1.2.21"
    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
}

version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven("http://repo.spring.io/milestone")
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    compile("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    compile("com.google.code.gson:gson:2.3.1")

    compile("org.apache.ws.commons.util:ws-commons-util:1.0.2")
    compile("org.apache.xmlrpc:xmlrpc-client:3.1.3")
    compile("org.apache.xmlrpc:xmlrpc-common:3.1.3")
    compile("org.springframework.boot:spring-boot-starter-security:$springBootVersion")

    compile("org.springframework:spring-web:5.0.2.RELEASE")

    compile("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.0")

    testCompile("org.springframework.boot:spring-boot-starter-test:$springBootVersion") {
        exclude(module = "junit")
    }
    testCompile("org.junit.jupiter:junit-jupiter-api:5.0.2")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:5.0.2")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
