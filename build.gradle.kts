plugins {
	java
	id("org.springframework.boot") version "4.1.1"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.UpgradingSkillsDemo"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(23)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	//Jwt Token Dependency
	implementation("io.jsonwebtoken:jjwt-api:0.12.6")
	//Mapper Dependency
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	//Spring data jpa for Sql Databases
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	//Spring-Data-Mongo for Mongo-Db
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	//Dependencies for  caching
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	//Spring -Security
	implementation("org.springframework.boot:spring-boot-starter-security")
	//for Validating the incoming data from client side through dto classes
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	//Lombok dependencies for short code
	compileOnly("org.projectlombok:lombok")
	//Spring dev tools
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	//Postgress-sql driver
	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")
	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor ("org.mapstruct:mapstruct-processor:1.5.5.Final")
	annotationProcessor ("org.projectlombok:lombok-mapstruct-binding:0.2.0")


	testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
	testImplementation("org.springframework.boot:spring-boot-starter-data-mongodb-test")
	testImplementation("org.springframework.boot:spring-boot-starter-data-redis-test")
	testImplementation("org.springframework.boot:spring-boot-starter-security-test")
	testImplementation("org.springframework.boot:spring-boot-starter-validation-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testCompileOnly("org.projectlombok:lombok")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testAnnotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
