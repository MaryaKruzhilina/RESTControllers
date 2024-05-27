package ru.maryKr.bootCrud.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maryKr.bootCrud.dao.UserDAO;
import ru.maryKr.bootCrud.model.User;

import java.util.List;

@Service
public class UserServiceImplement implements UserService {

    private final UserDAO userDAO;

    public UserServiceImplement(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @Transactional
    @Override
    public void addUser(User user) {
        userDAO.addUser(user);
    }

    @Transactional
    @Override
    public void removeUser(long id) {
        userDAO.removeUser(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getUsers() {
        return userDAO.getUsers();
    }

    @Transactional
    @Override
    public void updateUser(long id, User user) {
        userDAO.updateUser(id,user);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUser(long id) {
        return userDAO.getUser(id);
    }
}
