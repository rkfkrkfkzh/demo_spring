package demo.demo_spring.service;

import demo.demo_spring.domain.Member;
import demo.demo_spring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest // Spring Boot 애플리케이션의 통합 테스트를 지원하는 어노테이션입니다.
@Transactional // 각각의 테스트 메소드가 실행될 때마다 트랜잭션을 시작하고, 테스트가 완료되면 롤백하는 역할을 합니다. 이를 통해 테스트 간에 서로 영향을 주지 않고 독립적으로 실행될 수 있습니다.
class MemberServiceIntegrationTest {

    @Autowired // Spring IoC 컨테이너에서 해당 타입의 빈을 자동으로 주입받을 수 있도록 합니다
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
//Given
        Member member = new Member();
        member.setName("spring");
//When
        Long saveId = memberService.join(member);
//Then
        Member findMember = memberRepository.findById(saveId).get();
        assertEquals(member.getName(), findMember.getName());
    }

    @Test
    public void 중복_회원_예외() throws Exception {
//Given
        Member member1 = new Member();
        member1.setName("spring");
        Member member2 = new Member();
        member2.setName("spring");
//When
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}
