package io.github.nickdex.pasteit.domain.model;

/**
 * Model Class for Users
 *
 * @author Nikhil Warke
 */
public class User {

    private String id;

    private String firstName;

    private String lastName;

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
