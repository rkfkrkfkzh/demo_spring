package demo.demo_spring.domain;

public class Member { // 회원 정보를 저장하는 객체

    private Long id; // 회원의 고유한 ID를 저장하는 필드
    private String name; // 회원의 이름을 저장하는 필드

    public Long getId() { // 회원의 ID 값을 반환하는 메서드
        return id;
    }

    public void setId(Long id) { // 회원의 ID 값을 설정하는 메서드입니다. 매개변수로 회원의 ID 값을 전달
        this.id = id;
    }

    public String getName() { // 회원의 이름을 반환하는 메서드
        return name;
    }

    public void setName(String name) { // 회원의 이름을 설정하는 메서드입니다. 매개변수로 회원의 이름을 전달
        this.name = name;
    }
}
