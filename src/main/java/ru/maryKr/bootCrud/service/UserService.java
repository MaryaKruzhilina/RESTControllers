package ru.maryKr.bootCrud.service;


import ru.maryKr.bootCrud.model.User;

import java.util.List;

public interface UserService {
    void addUser(User user);
    void removeUser(long id);
    List<User> getUsers();
    void updateUser(long id, User user);
    User getUser(long id);
    public boolean isUsernameUnique(String username);
}
