package com.watayouxiang.demo.binder.ipc;

public class UserInfo {
    private String password;
    private String name;
    public UserInfo( ) {
    }

    public UserInfo(String password, String name) {
        this.password = password;
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
