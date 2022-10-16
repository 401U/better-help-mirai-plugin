plugins {
    val kotlinVersion = "1.7.0"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.12.3"
}

group = "cc.redme.mirai.plugin"
version = "1.0.0"

repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}
