package com.petmeet.petmeet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication = 이 클래스가 Spring Boot 애플리케이션의 시작점임을 선언하는 애노테이션.
// 내부적으로 3가지를 합친 것:
//   @SpringBootConfiguration  → 이 클래스를 설정 클래스로 등록
//   @EnableAutoConfiguration  → Spring이 필요한 설정을 자동으로 세팅 (DB, Web 등)
//   @ComponentScan            → 이 패키지 하위의 @Component, @Service, @Repository, @Controller를 자동 등록
@SpringBootApplication
public class PetmeetApplication {

	// 서버를 실행하면 가장 먼저 호출되는 메서드.
	// SpringApplication.run()이 내장 톰캣 웹 서버를 띄우고, Spring 컨테이너(IoC Container)를 초기화한다.
	public static void main(String[] args) {
		SpringApplication.run(PetmeetApplication.class, args);
	}

}
