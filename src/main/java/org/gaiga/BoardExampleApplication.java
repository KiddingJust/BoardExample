package org.gaiga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//EnableAutoConfiguration, @ComponentScan, @Configuration을 하나로 묶은 것 
@SpringBootApplication
@EnableJpaAuditing
public class BoardExampleApplication {

	//스프링부트 기동 위해서는 main 메소드가 필요한데, 여기에 아래 내용이 들어감. 
	//해당 annotation을 설정한 클래스가 있는 패키지를 최상위 패키지로 인식하고 ComponentScan을 수행
	public static void main(String[] args) {
		SpringApplication.run(BoardExampleApplication.class, args);
	}

}
