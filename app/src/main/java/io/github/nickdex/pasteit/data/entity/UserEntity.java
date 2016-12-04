package io.github.nickdex.pasteit.data.entity;

import io.github.nickdex.pasteit.core.data.entity.Entity;

/**
 * Model class to represent a user in data layer.
 */
public class UserEntity implements Entity {

    private String id;
    private String name;
    private String email;
    private boolean chrome = false;
    private boolean phone = false;

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasChrome() {
        return chrome;
    }

    public void setChrome(boolean chrome) {
        this.chrome = chrome;
    }

    public boolean hasPhone() {
        return phone;
    }

    public void setPhone(boolean phone) {
        this.phone = phone;
    }
}
