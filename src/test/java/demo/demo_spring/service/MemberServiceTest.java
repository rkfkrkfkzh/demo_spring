package demo.demo_spring.service;

import demo.demo_spring.domain.Member;
import demo.demo_spring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;


class MemberServiceTest {
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        // MemoryMemberRepository 클래스의 인스턴스를 생성하여 memberRepository 변수에 할당
        memberRepository = new MemoryMemberRepository();
        // MemberService 클래스의 생성자를 호출하여 memberRepository를 전달하여 memberService 변수에 할당
        memberService = new MemberService(memberRepository);
        /*
        @BeforeEach 어노테이션을 사용하여 각각의 테스트 메서드 실행 전에 실행되는 메서드를 정의
        각각의 테스트 메서드에서는 memberService를 사용하여 memberRepository에 접근
         */
    }

    @AfterEach
    public void afterEach() {
        // MemoryMemberRepository 클래스의 인스턴스인 memberRepository 객체의
        // clearStore() 메서드를 호출하여 해당 객체에 저장된 데이터를 모두 삭제
        memberRepository.clearStore();
        /*
        각각의 테스트 메서드에서는 memberRepository 객체에
        저장된 데이터에 의해 다음 테스트가 영향을 받지 않고, 독립적으로 수행
         */
    }

    @Test
    public void 회원가입() throws Exception {
        //Given
        // Member 클래스를 생성하고, 이름을 "hello"로 설정
        Member member = new Member();
        member.setName("hello");
        //When
        //memberService의 join 메소드를 호출하여, 회원가입을 진행합니다.
        //회원가입이 성공하면, 저장된 회원의 id값을 반환
        Long saveId = memberService.join(member);
        //Then
        //저장된 회원의 id값을 사용하여, 회원 조회를 진행합니다.
        //조회된 회원의 이름과, 처음에 생성한 Member 객체의 이름이 같은지를 검증합니다. (assertEquals 메소드 사용)
        Member findMember = memberRepository.findById(saveId).get();
        assertEquals(member.getName(), findMember.getName());
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //Given
        //Member 클래스를 생성하고, 이름을 각각 "spring"으로 설정합니다.
        //member1과 member2의 이름이 동일
        Member member1 = new Member();
        member1.setName("spring");
        Member member2 = new Member();
        member2.setName("spring");
        //When
        //memberService의 join 메소드를 두 번 호출합니다.
        //첫 번째 호출에서는 member1이 회원가입이 됩니다.
        //두 번째 호출에서는 member2가 회원가입을 시도합니다.
        //이때, 중복된 회원이므로 IllegalStateException 예외가 발생해야 합니다.
        //assertThrows 메소드를 사용하여 예외가 발생하는지를 검증
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.
        //Then
        //예외 메시지가 "이미 존재하는 회원입니다."인지를 검증합니다. assertThat 메소드를 사용하여 검증
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}