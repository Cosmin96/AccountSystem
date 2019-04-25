package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import exception.CustomException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private Long id;

    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @NotNull(message = "First name cannot be null")
    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    public User(){}

    public User(Long id, String username, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String username, String firstName, String lastName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        if(id == null) {
            throw new CustomException(Response.Status.FORBIDDEN, "User id cannot be null");
        }
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        if(username == null || username.equals("")) {
            throw new CustomException(Response.Status.FORBIDDEN, "Username cannot be null or empty");
        }
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        if(firstName == null || firstName.equals("")) {
            throw new CustomException(Response.Status.FORBIDDEN, "First name cannot be null or empty");
        }
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        if(lastName == null || lastName.equals("")) {
            throw new CustomException(Response.Status.FORBIDDEN, "Last name cannot be null or empty");
        }
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
