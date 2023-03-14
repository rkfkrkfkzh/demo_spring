package demo.demo_spring.controller;

import demo.demo_spring.domain.Member;
import demo.demo_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    // MemberService 타입의 의존성 주입을 받기 위해 생성자를 정의
    private final MemberService memberService;

    @Autowired
    // @Autowired 어노테이션을 사용하여 스프링 컨테이너에서 MemberService Bean을 주입받습니다.
    // 이렇게 의존성 주입을 받으면, MemberService Bean을 사용하여 비즈니스 로직을 처리할 수 있습니다.
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /*
    생성자에 final 키워드를 사용하여 의존성을 주입받은 후에는 해당 의존성을 변경할 수 없도록 합니다.
    이렇게 함으로써, MemberService Bean이 여러군데에서 사용될 때, 잘못된 인스턴스가 주입되는 경우를 방지할 수 있습니다.
    스프링 MVC에서 사용되는 MemberController 클래스를 정의하고, MemberService Bean을 의존성으로 주입받아 사용하고 있습니다.
    이를 통해 컨트롤러와 비즈니스 로직을 분리하고, 의존성 주입을 통해 느슨한 결합을 유지할 수 있습니다.
     */
    @GetMapping("/members/new") //HTTP GET 요청이 "/members/new" 경로로 들어올 경우
    public String creatForm() { // creatForm() 메소드를 실행하도록 매핑(mapping)
        return "/members/creatMembersForm"; // 회원가입 폼을 보여주는 뷰(View)를 반환
    }

    @PostMapping(value = "/members/new") // POST 요청이 "/members/new" 경로로 들어올 경우
    public String create(MemberForm form) { // create() 메소드를 실행하도록 매핑(mapping)
        /*
        MemberForm은 회원가입 폼에서 전달된 데이터를 받는 객체입니다.
        이 객체는 스프링에서 자동으로 생성되고, 전달된 데이터를 객체의 필드에 자동으로 매핑합니다.
        예를 들어, 회원가입 폼에서 전달된 "name"이라는 파라미터는 MemberForm의 "name" 필드에 자동으로 매핑됩니다.
         */
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member); // Member 객체를 인자로 받아서 해당 회원을 데이터베이스에 저장하는 등의 로직을 수행합니다.

        return "redirect:/"; // 회원가입 처리가 완료된 후, 홈페이지로 리다이렉트하는 명령입니다
    }

    @GetMapping("/members")
    public String list(Model model) { // Model 객체를 매개변수로 받습니다.
        // Model 객체는 뷰(View)에 데이터를 전달하기 위한 컨테이너입니다.

        List<Member> members = memberService.findMembers(); // Member 객체의 리스트를 가져옵니다.
        model.addAttribute("members", members); // "members"라는 이름으로 가져온 Member 객체의 리스트를 Model 객체에 추가
        return "members/membersList"; // "members/membersList"라는 뷰(View) 이름을 반환합니다.
        // 이 뷰는 Member 객체의 리스트를 화면에 출력하는 역할을 합니다.

    }
}
