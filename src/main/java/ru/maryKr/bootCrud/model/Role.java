package ru.maryKr.bootCrud.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private UserRole userRole;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();


    public Role() {
        ;
    }

    public Role(UserRole userName) {
        this.userRole = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<User> getUser() {
        return users;
    }

    public void setUser(Set<User> users) {
        this.users = users;
    }


    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole name) {
        this.userRole = name;
    }

    @Override
    public String getAuthority() {
        return userRole.name();
    }
}
