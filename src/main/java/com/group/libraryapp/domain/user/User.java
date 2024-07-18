package com.group.libraryapp.domain.user;

public class User {

    private String name;
    private Integer age;

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

}
