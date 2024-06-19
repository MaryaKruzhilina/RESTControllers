package ru.maryKr.bootCrud.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private UserRole userName;

//    @ManyToMany(mappedBy = "roles")
//    private Set<User> user = new HashSet<>();

    public Role() {
        ;
    }

    public Role(UserRole userName) {
        this.userName = userName;
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


    public UserRole getUserName() {
        return userName;
    }

    public void setUserName(UserRole name) {
        this.userName = name;
    }

    @Override
    public String getAuthority() {
        return userName.name();
    }
}
