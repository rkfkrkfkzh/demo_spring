package demo.demo_spring.controller;

import demo.demo_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {
    // MemberService 타입의 의존성 주입을 받기 위해 생성자를 정의
    private final MemberService memberService;
    @Autowired
    //@Autowired 어노테이션을 사용하여 스프링 컨테이너에서 MemberService Bean을 주입받습니다.
    // 이렇게 의존성 주입을 받으면, MemberService Bean을 사용하여 비즈니스 로직을 처리할 수 있습니다.
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }
    /*
    생성자에 final 키워드를 사용하여 의존성을 주입받은 후에는 해당 의존성을 변경할 수 없도록 합니다.
    이렇게 함으로써, MemberService Bean이 여러군데에서 사용될 때, 잘못된 인스턴스가 주입되는 경우를 방지할 수 있습니다.

    스프링 MVC에서 사용되는 MemberController 클래스를 정의하고, MemberService Bean을 의존성으로 주입받아 사용하고 있습니다.
    이를 통해 컨트롤러와 비즈니스 로직을 분리하고, 의존성 주입을 통해 느슨한 결합을 유지할 수 있습니다.
     */
}
