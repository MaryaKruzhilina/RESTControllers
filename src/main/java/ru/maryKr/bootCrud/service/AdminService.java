package ru.maryKr.bootCrud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.maryKr.bootCrud.configs.MyPasswordEncoder;
import ru.maryKr.bootCrud.model.User;
import ru.maryKr.bootCrud.repositiry.AdminRepository;

import java.util.List;

@Service
@Transactional
@Validated
public class AdminService {

    private final AdminRepository adminRepository;
    @Autowired
    private MyPasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return adminRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
    @Transactional
    public boolean isNotUsernameUnique(String username) {
        return adminRepository.findByUsername(username).isPresent();
    }

    @Transactional
    public void addUser(User user) {
        user.setPassword(passwordEncoder.getPasswordEncoder().encode(user.getPassword()));
        adminRepository.save(user);
    }

    @Transactional
    public void removeUser(long id) {
        adminRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return adminRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUser(long id) {
        return adminRepository.findById(id).orElse(null);
    }
}
