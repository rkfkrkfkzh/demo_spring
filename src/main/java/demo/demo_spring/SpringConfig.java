package demo.demo_spring;

import demo.demo_spring.repository.MemberRepository;
import demo.demo_spring.repository.MemoryMemberRepository;
import demo.demo_spring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// @Configuration 어노테이션은 해당 클래스가 스프링 설정 클래스임을 나타냅니다.
public class SpringConfig {
    @Bean
    // memberService() 메서드는 MemberService 타입의 Bean을 생성하고,
    // 이 Bean의 의존성으로 memberRepository() 메서드를 사용합니다.
    public MemberService memberService() {
        return new MemberService(memberRepository());

    }

    @Bean
    // MemoryMemberRepository 타입의 Bean을 생성하여 의존성 주입에 사용됩니다.
    public MemberRepository memberRepository() {
        // MemoryMemberRepository 타입의 Bean을 생성하여 반환합니다.
        return new MemoryMemberRepository();
    }
    /*
    MemberService와 MemberRepository Bean을 생성하여 스프링 컨테이너에서 관리하며,
    MemberService Bean은 MemberRepository Bean에 의존하고 있습니다.
     */
}
