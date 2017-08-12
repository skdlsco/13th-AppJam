package com.quiet.thequiet;

/**
 * Created by eka on 2017. 8. 13..
 */

public class User {
    String username;
    String id;
    String password;
    Boolean success;

    public User(String username, String id, String password) {
        this.username = username;
        this.id = id;
        this.password = password;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
