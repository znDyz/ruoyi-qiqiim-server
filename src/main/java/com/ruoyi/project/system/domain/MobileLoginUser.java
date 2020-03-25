package com.ruoyi.project.system.domain;

import java.io.Serializable;

public class MobileLoginUser implements Serializable {

    private String username;
    private String password;
    private String code;
    private String uuid;

    @Override
    public String toString() {
        return "MobileLoginUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", code='" + code + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }

    public MobileLoginUser() {}
    public MobileLoginUser(String username, String password, String code, String uuid) {
        this.username = username;
        this.password = password;
        this.code = code;
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCode() {
        return code;
    }

    public String getUuid() {
        return uuid;
    }
}
