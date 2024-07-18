package com.group.libraryapp.repository.user;

import org.springframework.jdbc.core.JdbcTemplate;

public class UserRepository { // Repository: DB 접근 담당

    public boolean isUserNotExist(JdbcTemplate jdbcTemplate, long id) {
        String readSql = "SELECT * FROM user WHERE id = ?";
        return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, id).isEmpty(); // isEmpty(): id가 존재하지 않는 경우(비어있는 경우) true 반환, id가 존재하는 경우(비어있지 않은 경우) false 반환

    }

    public void updateUserName(JdbcTemplate jdbcTemplate, String name, long id) {
        String sql = "UPDATE user SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, name, id);
    }

}
