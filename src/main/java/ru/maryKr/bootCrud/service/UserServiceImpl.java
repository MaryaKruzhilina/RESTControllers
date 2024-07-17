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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Autowired
    private MyPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
    }
}
