package com.enu;

import java.io.Serializable;

public enum ErrorCode implements Serializable {

    SUCCESS("Thanh Cong"),
    FAIL("That bai"),
    USERNAME_EXISTED("userName da duoc su dung"),
    EMAIL_EXISTED("email da duoc su dung"),
    ;

    private String description;

    ErrorCode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
