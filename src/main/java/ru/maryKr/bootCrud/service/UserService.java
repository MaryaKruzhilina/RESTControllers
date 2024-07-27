package ru.maryKr.bootCrud.service;

import ru.maryKr.bootCrud.model.User;

public interface UserService {
    User findByEmail(String email);
}
