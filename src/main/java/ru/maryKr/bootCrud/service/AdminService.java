package ru.maryKr.bootCrud.service;

import ru.maryKr.bootCrud.model.User;

import java.util.List;

public interface AdminService {
    public User findByUsername(String username);
    public boolean isNotUsernameUnique(String username);
    public void addUser(User user);
    public void removeUser(long id);
    public List<User> getUsers();
    public User getUser(long id);
}
