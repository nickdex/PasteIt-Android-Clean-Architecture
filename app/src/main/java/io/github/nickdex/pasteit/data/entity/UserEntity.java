package io.github.nickdex.pasteit.data.entity;

import io.github.nickdex.pasteit.core.data.entity.Entity;

/**
 * Model class to represent a user in data layer.
 */
public class UserEntity implements Entity {

    private String id;

    private String firstName;

    private String lastName;

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
