package demo.demo_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Spring Boot 애플리케이션의 구성 요소들을 자동으로 구성하고,
// 실행 클래스에서 이 애노테이션을 사용함으로써 Spring Boot 애플리케이션을 시작할 수 있습니다.
public class DemoSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringApplication.class, args);
    }

}
