package demo.demo_spring.repository;

import demo.demo_spring.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    //EntityManager는 JPA에서 가장 중요한 객체로, 엔티티 매니저를 생성하고 관리하는 역할
    private final EntityManager em;

    /*
    EntityManager 객체를 생성자에서 인자로 받아서 초기화합니다.
    따라서, EntityManager 객체를 사용하여 데이터베이스와 상호작용할 수 있습니다.
     */
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        //전달받은 member 객체를 데이터베이스에 저장
        //persist 메서드를 호출하면, Member 객체의 @Id 어노테이션에 지정된 필드가 자동으로 생성된 ID로 업데이트됩니다.
        //이후 member 객체가 반환되며, 이는 데이터베이스에 저장된 Member 객체와 동일한 ID 값을 가지게 됩니다.
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name=:name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select  m from Member m", Member.class).getResultList();
    }
}
