package demo.demo_spring.service;

import demo.demo_spring.domain.Member;
import demo.demo_spring.repository.MemberRepository;
import demo.demo_spring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    // MemberRepository 인터페이스를 구현한 구현체를 주입받아 멤버 변수로 저장
    private final MemberRepository memberRepository;
    // MemberService 클래스의 생성자입니다.
    // MemberRepository 인터페이스를 구현한 구현체를 매개변수로 받아서 memberRepository 멤버 변수에 할당합니다.
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    // private final MemberRepository memberRepository = new MemoryMemberRepository();
    /*
    MemoryMemberRepository 클래스를 이용하여 MemberRepository 인터페이스를 구현한
    memberRepository 인스턴스를 생성합니다.
    MemoryMemberRepository 클래스는 MemberRepository 인터페이스를 구현하며,
    메모리에 데이터를 저장하는 방식으로 동작합니다.
     */

    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
        /*
        Member 인스턴스를 전달받아, 중복되는 회원이 아닌 경우
        memberRepository 인스턴스의 save 메소드를 이용하여 데이터를 저장하고,
        해당 회원의 id를 반환합니다.
         */
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
        /*
        Member 인스턴스를 전달받아, memberRepository 인스턴스의 findByName 메소드를
        이용하여 동일한 이름을 가진 회원이 존재하는지 검사합니다.
        동일한 이름을 가진 회원이 존재하는 경우, IllegalStateException 예외를 던집니다.
         */
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
        /*
        memberRepository 인스턴스의 findAll 메소드를 이용하여 저장된 모든 회원 정보를 조회합니다.
         */
    }

    public Optional<Member> findOne(Long memberid) {
        return memberRepository.findById(memberid);
        /*
        회원의 id를 전달받아, memberRepository 인스턴스의 findById 메소드를 이용하여 해당 회원 정보를 조회합니다.
        조회 결과는 Optional 타입으로 반환
         */
    }
}
