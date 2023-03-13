package demo.demo_spring.repository;

import demo.demo_spring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository { // MemberRepository"는 "Member" 객체를 저장하고 조회하기 위한 메소드를 정의
    Member save(Member member); //  Member 객체를 저장하는 메소드. 저장된 Member 객체를 반환

    Optional<Member> findById(Long id); // id를 기반으로 Member 객체를 찾는 메소드. Optional<Member>를 반환

    Optional<Member> findByName(String name); // name을 기반으로 Member 객체를 찾는 메소드. Optional<Member>를 반환

    List<Member> findAll(); // 모든 Member 객체를 반환하는 메소드. List<Member>를 반환
    /*
    실제로 Member 객체를 저장하고 조회하는 기능을 구현
    Optional 객체는 값이 null일 수도 있는 객체를 나타내며, 이를 통해 NullPointerException을 방지
     */
}
