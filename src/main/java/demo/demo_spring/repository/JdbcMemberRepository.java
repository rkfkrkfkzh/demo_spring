package demo.demo_spring.repository;

import demo.demo_spring.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository {
    /*
    JDBC를 사용하여 데이터베이스와 상호 작용하는 데 사용되는 인터페이스입니다.
    이 클래스의 생성자에서는 DataSource 객체가 생성자 인수로 전달됩니다.
    dataSource 필드가 불변성을 가지며, 이 필드를 변경할 수 없음
     */
    private final DataSource dataSource;

    public JdbcMemberRepository(DataSource dataSource) {
        //생성자 내부에서 전달된 dataSource 객체를 이 클래스의 인스턴스 변수 dataSource에 할당
        this.dataSource = dataSource;
    }

    @Override
    public Member save(Member member) {
        // SQL 쿼리 문자열을 정의하는 코드입니다. member 테이블에 name 필드에 Member 객체의 이름을 삽입
        String sql = "insert into member(name) values(?)";
        Connection conn = null; // 데이터베이스 연결
        PreparedStatement pstmt = null; // 쿼리 수행을 위한 PreparedStatement 객체
        ResultSet rs = null; // 생성된 멤버의 ID 값을 저장할 ResultSet 객체
        try {
            //DataSource 객체를 사용하여 데이터베이스와 연결 이를 통해 conn 변수에 데이터베이스 연결이 할당
            conn = getConnection();
            //DB에 대한 PreparedStatement 객체를 생성
            pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            //name 필드에 Member 객체의 이름을 할당
            pstmt.setString(1, member.getName());
            //쿼리를 실행하고 영향을 받은 행의 수를 반환
            pstmt.executeUpdate();
            //자동 생성된 ID 값을 ResultSet 객체에서 검색
            rs = pstmt.getGeneratedKeys();
            //ResultSet 객체에서 자동 생성된 ID 값을 검색하고, 해당 값이 존재하면 Member 객체에 할당
            if (rs.next()) {
                member.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            //데이터베이스 연결, PreparedStatement 객체, ResultSet 객체를 종료하는 코드입니다.
            // 이 메서드는 데이터베이스와의 연결을 해제하고, 자원을 반환
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findById(Long id) { // id를 매개변수로 받아 회원 정보를 조회
        String sql = "select * from member where id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql); // ?에 매핑할 ID 값을 인자로 전달
            pstmt.setLong(1, id); //id를 쿼리의 ?에 매핑된 값을 지정
            rs = pstmt.executeQuery(); //쿼리를 실행하고 결과셋을 반환
            if (rs.next()) { // ResultSet 객체에서 결과 레코드가 존재하는지 확인
                Member member = new Member(); // Member 객체를 생성
                //ResultSet에서 해당하는 칼럼 값을 꺼내어 Member 객체의 속성에 설정
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member); //Member 객체를 Optional 객체로 감싸서 반환
            } else {
                return Optional.empty(); //Optional.empty() 메소드를 호출하여 빈 Optional 객체를 반환
            }
        } catch (Exception e) {
            throw new IllegalStateException(e); //예외가 발생하면, IllegalStateException을 던지고 종료
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from member"; // member 테이블의 모든 열을 검색
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            //List<Member> : Member 객체를 저장할 List 타입
            // ArrayList<>() : Member 객체를 저장할 ArrayList 객체를 생성
            List<Member> members = new ArrayList<>();
            while (rs.next()) { // ResultSet에서 데이터를 한 행씩 조회
                Member member = new Member(); //Member 객체를 생성
                member.setId(rs.getLong("id")); // ResultSet에서 id 열 값을 가져와 Member 객체에 설정
                member.setName(rs.getString("name"));// ResultSet에서 name 열 값을 가져와 Member 객체에 설정
                members.add(member);// Member 객체를 List<Member>에 추가
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name); //쿼 리의 첫 번째 매개 변수를 name 값으로 설정
            rs = pstmt.executeQuery(); // 쿼리를 실행하고 결과 집합(ResultSet)을 반환
            if (rs.next()) { //메서드를 사용하여 결과 집합의 첫 번째 행으로 이동하고,
                // 해당 행이 존재하는 경우 Member 객체를 만들고 결과 값을 채웁니다.
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);//결과 값이 있으면 해당 값으로 Optional 객체를 만들고
            }
            return Optional.empty();//그렇지 않으면 빈 Optional 객체를 반환
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
        /*
        이 코드는 데이터베이스 연결 및 쿼리 실행에 대한 기본 예외 처리를 수행하며,
        결과를 Optional 클래스를 사용하여 처리하는 좋은 예제입니다.
        그러나 SQLException 등과 같은 데이터베이스 관련 예외를 처리하지 않고
        모든 예외를 IllegalStateException으로 처리하는 것은 일반적으로 권장되지 않습니다.
        데이터베이스 예외를 적절하게 처리하여 애플리케이션을 안정적으로 유지하는 것이 중요합니다.
         */
    }

    //DataSourceUtils 객체의 멤버 변수인 dataSource를 이용하여 데이터베이스 연결 객체(Connection)를 가져옵니다.
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource); //가져온 연결 객체(Connection)를 반환
    }

    //인자로 전달된 Connection 객체, PreparedStatement 객체, ResultSet 객체를 받습니다.
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close(Connection conn) throws SQLException {
        //전달된 Connection 객체를 데이터 소스에서 해제
        //SQLException 예외가 발생할 경우 이를 던지게 됩니다.
        DataSourceUtils.releaseConnection(conn, dataSource);
        // JDBC 연결 해제와 관련된 작업을 수행
    }
}