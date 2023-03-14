package demo.demo_spring.controller;

public class MemberForm { //이 클래스는 회원 등록 폼에서 입력 받은 이름 정보를 담기 위한 모델 클래스
    private String name; //name 필드는 회원의 이름 정보를 담습니다.
    // getter와 setter 메소드를 갖고 있으며, 이를 통해 name 필드에 대한 접근을 가능하게 합니다.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    /*
    컨트롤러에서 회원 등록 폼에서 입력된 값을 바인딩하여 사용하게 됩니다.
    예를 들어, MemberForm 객체의 name 필드에 회원 이름이 입력되면,
    컨트롤러에서 MemberForm 객체를 생성하여 name 필드를 바인딩하고,
    이를 이용하여 새로운 회원 정보를 생성합니다.
     */
}
