package ru.maryKr.bootCrud.dao;


import ru.maryKr.bootCrud.model.User;

import java.util.List;

public interface UserDAO {
    void addUser(User user);
    void removeUser(long id);
    List<User> getUsers();
    void updateUser(long id, User user);
    User getUser(long id);
}
