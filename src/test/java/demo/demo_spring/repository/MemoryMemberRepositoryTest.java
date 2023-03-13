package demo.demo_spring.repository;

import demo.demo_spring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {

    // MemoryMemberRepository 클래스의 인스턴스를 생성하고, 이를 repository 변수에 할당
    MemoryMemberRepository repository = new MemoryMemberRepository();
    /*
    MemoryMemberRepository 클래스는 회원 정보를 메모리에 저장하는 기능을 제공하는 클래스입니다.
    따라서, repository 변수는 이 클래스의 인스턴스를 참조
    이후에는 repository 변수를 이용하여 MemoryMemberRepository 클래스에서 제공하는
    다양한 메서드를 호출하여 회원 정보를 추가, 수정, 삭제, 조회 등의 작업을 수행
     */

    @AfterEach  // 테스트 메서드가 실행된 후에 수행되는 메서드
    public void afterEach() {
        repository.cleatStore(); // repository.clearStore() 메서드는 MemoryMemberRepository 클래스에 구현된 메서드로,
        // 해당 객체가 가지고 있는 메모리 저장소(store)를 비워주는 역할
    /*
    @AfterEach 어노테이션이 붙은 afterEach() 메서드는 각각의 테스트 메서드가 실행된 후에
    repository 객체가 가지고 있는 저장소를 비워주는 역할을 합니다.
    이를 통해 각각의 테스트가 서로 영향을 미치지 않고 독립적으로 실행
    */
    }

    @Test
    public void save() {
        Member member = new Member(); // Member 객체를 생성
        member.setName("spring"); // 이름을 "spring"으로 설정

        repository.save(member); // MemoryMemberRepository 객체의 save() 메소드를 호출하여 해당 Member 객체를 저장

        // repository.findById() 메소드를 이용하여 저장된 Member 객체를 다시 가져온 뒤,
        // Optional 클래스의 get() 메소드를 이용하여 Member 객체를 추출
        Member result = repository.findById(member.getId()).get();
        // assertThat() 메소드를 이용하여 원래 생성한 Member 객체와 가져온 Member 객체가 같은지를 검증합니다.
        // isEqualTo() 메소드를 이용하여 두 객체가 같은지 검증
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByname() {
        Member member1 = new Member(); // member1 객체를 생성
        member1.setName("spring1"); //"spring1"로 설정
        repository.save(member1); // repository에 저장

        Member member2 = new Member(); // member2 객체를 생성
        member2.setName("spring2"); // "spring2"로 설정
        repository.save(member2); // repository에 저장

        //이름이 "spring1"인 회원을 조회합니다.
        // 이때, get() 메서드를 사용하여 Optional 타입을 벗겨내고 실제 Member 객체를 반환
        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1); // 조회한 회원(result)이 member1과 동일한 객체인지를 확인
        // isEqualTo() 메서드는 result와 member1의 내용이 동일한지를 비교하고, 그 결과에 따라 테스트가 성공 또는 실패
    }

    @Test
    public void findAll() {
        Member member1 = new Member(); // member1 객체를 생성
        member1.setName("spring1"); //"spring1"로 설정
        repository.save(member1); // Member 객체를 데이터베이스에 저장

        Member member2 = new Member(); // member2 객체를 생성
        member2.setName("spring2"); // "spring2"로 설정
        repository.save(member2); // Member 객체를 데이터베이스에 저장

        List<Member> result = repository.findAll(); //데이터베이스에서 모든 Member 객체를 가져옵니다.

        //  Member 객체의 개수가 2개인지 확인합니다.
        //  이를 위해 JUnit의 assertThat() 메소드와 isEqualTo() 메소드를 사용
        assertThat(result.size()).isEqualTo(2);
    }
}
