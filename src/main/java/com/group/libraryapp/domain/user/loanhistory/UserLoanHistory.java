package com.group.libraryapp.domain.user.loanhistory;

import com.group.libraryapp.domain.user.User;

import javax.persistence.*;

@Entity
public class UserLoanHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @ManyToOne // 히스토리 입장: N(히스토리) : 1(유저)
    private User user;

    private String bookName;

    private boolean isReturn; // DB에 1(true)인 경우 반납한 경우, 0(false)인 경우 반납되지 않은 경우

    protected UserLoanHistory() {
    }

    public UserLoanHistory(User user, String bookName) {
        this.user = user;
        this.bookName = bookName;
        this.isReturn = false;
    }

    public void doReturn() {
        this.isReturn = true;
    }

    public String getBookName() {
        return this.bookName;
    }
}
