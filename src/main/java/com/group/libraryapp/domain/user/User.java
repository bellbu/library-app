package com.group.libraryapp.domain.user;

import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // @Entity: 스프링의 객체와 DB의 테이블을 매핑
public class User {

    @Id // 해당 필드를 pk로 간주
    @GeneratedValue(strategy = GenerationType.IDENTITY) // pk 자동 생성: strategy = GenerationType.IDENTITY(MySQL의 auto_increment와 동일)
    private Long id = null;

    @Column(nullable = false, length = 20) // name varchar(20)
    private String name;

    private Integer age; // age는 null이 올 수 있고, Integer는 DB의 int와 동일하므로 @Column 생략 가능

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) // 유저 입장: 1(유저[부모]) : N[자식] / 연관관계의 주인이 아닌쪽에 mappedBy 설정
    private List<UserLoanHistory> userLoanHistories = new ArrayList<>();

    protected User() {}  // JPA 사용하기 위해선 기본 생성자 필요

    public User(String name, Integer age) {
        if (name == null || name.isBlank()) {                                                       // IllegalArgumentException: 메서드에 잘못된 인수가 전달되었을 때 발생하는 예외
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다.", name)); // String.format(): 리턴되는 문자열의 형태를 지정하는 메소드
        }
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Long getId() {
        return id;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void loanBook(String bookName) { // 도메인 계층에 비즈니스 로직 작성함
        this.userLoanHistories.add(new UserLoanHistory(this, bookName));
    }

    public void returnBook(String bookName) {
        UserLoanHistory targetHistory = this.userLoanHistories.stream() // (select * from user_loan_history where user_id = ?)
                .filter(history -> history.getBookName().equals(bookName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        targetHistory.doReturn();
    }

}
