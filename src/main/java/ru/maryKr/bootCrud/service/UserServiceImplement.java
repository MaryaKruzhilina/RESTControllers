package ru.maryKr.bootCrud.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maryKr.bootCrud.dao.UserDAO;
import ru.maryKr.bootCrud.model.Role;
import ru.maryKr.bootCrud.model.User;
import ru.maryKr.bootCrud.model.UserRole;
import ru.maryKr.bootCrud.repositiry.UserRepository;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImplement implements UserService {

    private final UserDAO userDAO;

    private final UserRepository userRepository;

    public UserServiceImplement(UserDAO userDAO, UserRepository userRepository){
        this.userRepository = userRepository;
        this.userDAO = userDAO;
    }
    public User findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean isUsernameUnique(String username){
        return findUserByUsername(username) != null;
    }

    @Transactional
    @Override
    public void addUser(User user) {
        userRepository.save(user);
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
