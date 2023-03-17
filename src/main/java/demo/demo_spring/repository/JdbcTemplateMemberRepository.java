package demo.demo_spring.repository;

import demo.demo_spring.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository {

    //JdbcTemplate 클래스의 인스턴스를 저장하기 위한 멤버 변수를 선언하는 코드
    private final JdbcTemplate jdbcTemplate; //멤버 변수는 이후 JdbcTemplate 객체를 사용하여 데이터베이스 액세스를 수행할 때 사용

    //클래스의 생성자입니다. 이 생성자는 DataSource 객체를 인자로 받아서,
    // JdbcTemplate 객체를 생성하고 jdbcTemplate 멤버 변수를 초기화
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        //SimpleJdbcInsert 객체를 생성하여 jdbcTemplate을 이용하여 데이터베이스에 INSERT 쿼리를 수행
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        //withTableName 메서드를 사용하여 데이터를 저장할 테이블 이름을 지정
        //usingGeneratedKeyColumns 메서드를 사용하여 자동으로 생성된 키 값을 받을 컬럼 이름을 지정
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
        //Map 객체를 생성하여 데이터베이스에 저장할 member 객체의 이름을 매핑
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());
        //jdbcInsert.executeAndReturnKey 메서드를 사용하여 데이터베이스에 INSERT 쿼리를 실행하고,
        // 자동으로 생성된 키 값을 반환
        Number key = jdbcInsert.executeAndReturnKey(new
                MapSqlParameterSource(parameters));
        //반환된 키 값을 member 객체의 id에 저장하고, member 객체를 반환
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        //query 메서드는 SQL 쿼리문과 결과를 매핑할 RowMapper 객체, 그리고 SQL 쿼리문의 파라미터를 가변인자로 받습니다.
        //select * from member where id= ?는 member 테이블에서 id 값이 ?인 레코드를 조회하는 SQL 쿼리문
        //?은 SQL 쿼리문의 파라미터 위치를 나타내며, 두 번째 인자인 id 값으로 치환
        //memberRowMapper() 메서드는 RowMapper<Member> 인터페이스를 구현한 객체를 반환
        List<Member> result = jdbcTemplate.query("select * from member where id= ?",
                memberRowMapper(), id);
        return result.stream().findAny(); // 조회된 Member 객체를 Optional 형태로 반환
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?",
                memberRowMapper(), name);
        return result.stream().findAny();
    }

    //ResultSet에서 Member 객체로 변환하는 RowMapper 객체를 반환하는 memberRowMapper() 메서드
    //RowMapper는 query 메서드에 의해 호출되며, 조회된 ResultSet의 각 레코드를 매핑하는 역할
    //람다식을 사용하여 RowMapper<Member> 인터페이스를 구현한 객체를 생성
    private RowMapper<Member> memberRowMapper() {
        //rs는 ResultSet 객체이며, rowNum은 현재 처리되고 있는 레코드의 인덱스를 의미
        return (rs, rowNum) -> {
            Member member = new Member();
            //rs.getLong("id")와 rs.getString("name")은 ResultSet 객체에서 id와 name 컬럼의 값을 읽어오는 메서드
            //읽어온 값을 Member 객체의 필드에 설정
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member; //이를 반환
        };
    }
}