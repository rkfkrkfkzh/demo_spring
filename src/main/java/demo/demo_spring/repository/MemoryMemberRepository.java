package demo.demo_spring.repository;

import demo.demo_spring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

// 회원 정보를 메모리에 저장하고, 회원 정보를 조회하는 기능을 구현한 클래스
public class MemoryMemberRepository implements MemberRepository {
    // 회원 정보를 저장하는 Map 객체입니다. Key 값으로 회원의 ID 값을 사용하며,
    // Value 값으로는 회원 객체(Member)를 사용
    private static Map<Long, Member> store = new HashMap<>();
    // 회원의 ID 값을 생성하기 위한 변수입니다. 이 값은 0부터 시작해서 회원이 추가될 때마다 1씩 증가
    private static long sequence = 0L;

    //회원 정보를 저장하는 메서드입니다. 매개변수로는 저장할 회원 정보(Member) 객체를 전달받습니다.
    // 이 메서드는 회원의 ID 값을 생성하고, Map 객체에 회원 정보를 저장한 뒤 회원 정보를 반환
    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    // 주어진 ID 값을 가진 회원 정보를 찾아서 Optional 객체로 반환하는 메서드입니다.
    // 매개변수로는 찾을 회원의 ID 값을 전달받습니다.
    // Map 객체에서 주어진 ID 값을 가진 회원 정보를 찾아 Optional 객체로 반환합니다.
    // 만약 찾는 회원 정보가 없다면 Optional.empty()를 반환
    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    // 주어진 이름을 가진 회원 정보를 찾아서 Optional 객체로 반환하는 메서드입니다.
    // 매개변수로는 찾을 회원의 이름을 전달받습니다.
    // Map 객체에서 이름이 일치하는 회원 정보를 찾아 Optional 객체로 반환합니다.
    // 만약 찾는 회원 정보가 없다면 Optional.empty()를 반환
    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    // 현재 저장되어 있는 모든 회원 정보를 List 형태로 반환하는 메서드입니다.
    // Map 객체에서 Value 값(회원 정보)을 모두 추출하여 ArrayList로 반환
    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    /*
    메모리에 저장된 회원 정보를 조회하고, 저장하는 기능을 구현하며,
    "MemberRepository" 인터페이스를 구현하여 다른 클래스에서 이 기능을 사용할 수 있도록 제공
     */
    public void clearStore() {
        store.clear();
    }
}
