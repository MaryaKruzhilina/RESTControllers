package ru.maryKr.bootCrud.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.maryKr.bootCrud.configs.MyPasswordEncoder;
import ru.maryKr.bootCrud.model.User;
import ru.maryKr.bootCrud.repositiry.UserRepository;

import java.util.List;

@Service
@Validated
public class UserService {

    private final UserRepository userRepository;
    @Autowired
    private MyPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
    @Transactional
    public boolean isNotUsernameUnique(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Transactional
    public void addUser(User user) {
        user.setPassword(passwordEncoder.getPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void removeUser(long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUser(long id) {
        return userRepository.findById(id).orElse(null);
    }
}
