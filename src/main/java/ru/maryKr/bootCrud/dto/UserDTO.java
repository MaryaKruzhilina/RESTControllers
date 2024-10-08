package ru.maryKr.bootCrud.dto;

import ru.maryKr.bootCrud.model.Role;
import ru.maryKr.bootCrud.model.User;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {
    private long id;
    private String name;
    private String email;
    private int age;
    private String lastname;
    Set<String> roles = new HashSet<>();

    public UserDTO() {
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.age = user.getAge();
        this.lastname = user.getLastname();
        for (Role role : user.getRoles()) {
            roles.add(role.getUserRole().toString().replace("ROLE_", ""));
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
