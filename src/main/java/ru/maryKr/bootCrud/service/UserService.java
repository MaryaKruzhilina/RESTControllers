package ru.maryKr.bootCrud.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.maryKr.bootCrud.model.User;

public interface UserService {
    User findByEmail(String email);
}
