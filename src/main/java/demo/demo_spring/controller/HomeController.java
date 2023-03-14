package demo.demo_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    // HTTP GET 요청이 "/" 경로로 들어올 경우,해당 메소드인 home() 메소드를 실행하도록 매핑(mapping)합니다.
    @GetMapping("/")
    public String home() {
        return "home"; //"home" 문자열을 반환합니다.
        // 이는 스프링에서 view resolver가 "home"이라는 뷰(View)를 찾아서 사용자에게 보여줍니다.
    }
}
