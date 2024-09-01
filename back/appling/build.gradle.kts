plugins {
	java
	id("org.springframework.boot") version "3.4.0-M2"
	id("io.spring.dependency-management") version "1.1.6"
	id("jacoco")
}

group = "com.simol"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
	implementation("org.springdoc:springdoc-openapi-starter-webflux-api:2.5.0")
	compileOnly("org.projectlombok:lombok")
//	developmentOnly("org.springframework.boot:spring-boot-docker-compose")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("com.mysql:mysql-connector-j")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport)
}

jacoco {
	toolVersion = "0.8.12" // JaCoCo의 버전을 명시합니다.
}

tasks.jacocoTestReport {
	dependsOn(tasks.test) // 테스트가 완료된 후 리포트를 생성하도록 설정

	reports {
		xml.required.set(true) // XML 리포트 생성을 활성화
		csv.required.set(false) // CSV 리포트 생성을 비활성화
		html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml")) // HTML 리포트의 출력 위치 설정
	}
}

tasks.jacocoTestCoverageVerification {
	violationRules {
		rule {
			limit {
				counter = "LINE"
				value = "COVEREDRATIO"
				minimum = "0.80".toBigDecimal() // 최소 80% 이상의 라인 커버리지를 요구함
			}
		}
	}
}

tasks.check {
	dependsOn(tasks.jacocoTestCoverageVerification) // `check` 태스크가 실행될 때 커버리지 검증을 포함하도록 설정
}