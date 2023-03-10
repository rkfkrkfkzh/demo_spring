package demo.demo_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // 해당 클래스가 Spring MVC의 컨트롤러임을 나타냅니다.
public class HelloController {
    @GetMapping("hello") // HTTP GET 요청이 "/hello" 경로로 들어왔을 때 이 메서드가 호출
    public String hello(Model model) { // Model 객체를 매개변수로 받으며, 이 객체를 사용하여 뷰에 데이터를 전달
        // "hello" 문자열을 값으로 갖는 "data"라는 이름의 속성을 Model 객체에 추가
        model.addAttribute("data", "hello!");
        return "hello"; // "hello" 문자열을 반환
    }

    @GetMapping("hello-mvc") //HTTP GET 요청이 "/hello-mvc" URI로 들어오면 이 메소드를 실행하도록 지정
    //@RequestParam("name") 어노테이션은 "name"이라는 요청 매개 변수(parameter) 값을 가져와서 name 변수에 할당
    //Model 객체는 Spring MVC에서 View에 데이터를 전달하기 위한 객체
    public String helloMvc(@RequestParam("name") String name, Model model) {
        // model.addAttribute("name", name) 코드는 "name"이라는 속성(attribute)을 추가하고, 해당 값으로 name 변수의 값을 전달
        model.addAttribute("name", name);
        return "hello-template";// "hello-template" 문자열을 반환
    }
}
