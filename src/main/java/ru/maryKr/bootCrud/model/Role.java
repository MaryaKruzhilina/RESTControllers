package ru.maryKr.bootCrud.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private UserRole name;

//    @ManyToMany(mappedBy = "roles")
//    private Set<User> user = new HashSet<>();

    public Role() {
        ;
    }

    public Role(UserRole name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public Set<User> getUser() {
//        return user;
//    }
//
//    public void setUser(Set<User> user) {
//        this.user = user;
//    }

    public UserRole getRole() {
        return name;
    }

    @Override
    public String getAuthority() {
        return name.toString();
    }
}
