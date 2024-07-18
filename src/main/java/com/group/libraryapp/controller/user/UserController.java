package com.group.libraryapp.controller.user;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.service.user.UserService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // @RestController: @Controller + @ResponseBody(json 형태로 데이터를 반환해 줌)
public class UserController { // Controller: API와 HTTP 담당

    private final UserService userService = new UserService();
    private final JdbcTemplate jdbcTemplate;  // jdbcTemplate 선언

    public UserController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/user") // 등록
    public void saveUser(@RequestBody UserCreateRequest request) { // @RequestBody: HTTP 요청의 바디에 담긴 값들을 자바객체로 변환시켜 객체에 저장
        String sql = "INSERT INTO user (name, age) VALUES (?, ?)";
        jdbcTemplate.update(sql, request.getName(), request.getAge()); // .update(): INSERT, UPDATE, DELETE 쿼리에 사용
    }

    @GetMapping("/user") // 목록보기
    public List<UserResponse> getUsers() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, (rs, rowNum) -> { // 두번째 익명 클래스(익명 객체)를 람다식으로 변환 RowMapper
            long id = rs.getLong("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            return new UserResponse(id, name, age);
        });

        /* 첫번째
        List<UserResponse> responses = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            responses.add(new UserResponse(i+1, users.get(i)));
        }
        return responses;
        */

        /* 두번째
        return jdbcTemplate.query(sql, new RowMapper<UserResponse>() { // sql 결과들을 UserResponse 객체로 반환, RowMapper는 함수형 인터페이스
            @Override
            public UserResponse mapRow(ResultSet rs, int rowNum) throws SQLException { // mapROW: sql 결과를 UserResponse 객체로 매핑하여 결과를 리턴
                long id = rs.getLong("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                return new UserResponse(id, name, age);
            }
        });
        */
    }

    @PutMapping("/user") // 수정
    public void updateUser(@RequestBody UserUpdateRequest request) { // 객체화
        userService.updateUser(jdbcTemplate, request);
    }

    @DeleteMapping("/user") // 삭제
    public void deleteUser(@RequestParam String name) {
        String readSql = "SELECT * FROM user WHERE name = ?";
        boolean isUserNotExist = jdbcTemplate.query(readSql, (rs, rowNum) -> 0, name).isEmpty(); // isEmpty(): 비어있는 경우 true 반환, 비어있지 않은 경우 false 반환
        if (isUserNotExist) {
            throw new IllegalArgumentException();
        }

        String sql = "DELETE FROM user WHERE name = ?";
        jdbcTemplate.update(sql, name);
    }


}
