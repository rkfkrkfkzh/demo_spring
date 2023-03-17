package demo.demo_spring;

import demo.demo_spring.repository.*;
import demo.demo_spring.service.MemberService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
// @Configuration 어노테이션은 해당 클래스가 스프링 설정 클래스임을 나타냅니다.
public class SpringConfig {

    private EntityManager em;
    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

//    private DataSource dataSource; //접근 제한자를 사용하여 DataSource 객체를 멤버 변수로 선언
//
//    @Autowired
//    //@Autowired 어노테이션을 사용하여 Spring에서 DataSource 객체를 자동으로 주입받도록 합니다.
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource; //DataSource 객체를 매개변수로 받아와서 멤버 변수에 할당
//    }



    @Bean
    // memberService() 메서드는 MemberService 타입의 Bean을 생성하고,
    // 이 Bean의 의존성으로 memberRepository() 메서드를 사용합니다.
    public MemberService memberService() {
        return new MemberService(memberRepository());

    }

    @Bean
    // MemoryMemberRepository 타입의 Bean을 생성하여 의존성 주입에 사용됩니다.
    public MemberRepository memberRepository() {
          //MemoryMemberRepository 타입의 Bean을 생성하여 반환합니다.
//        return new MemoryMemberRepository();

        // dataSource 매개변수를 이용하여 JdbcMemberRepository 객체를 새로 생성하고 반환
//        return new JdbcMemberRepository(dataSource);

//        return new JdbcTemplateMemberRepository(dataSource);

    return new JpaMemberRepository(em);
    }
    /*
    MemberService와 MemberRepository Bean을 생성하여 스프링 컨테이너에서 관리하며,
    MemberService Bean은 MemberRepository Bean에 의존하고 있습니다.
     */
}
