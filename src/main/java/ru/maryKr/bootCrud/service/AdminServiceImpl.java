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
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    @Autowired
    private MyPasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminRepository adminRepository) {

        this.adminRepository = adminRepository;
    }
    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return adminRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
    }
    @Override
    @Transactional
    public boolean isNotEmailUnique(String email) {
        return adminRepository.findByEmail(email).isPresent();
    }
    @Override
    @Transactional
    public void addUser(User user) {
        user.setPassword(passwordEncoder.getPasswordEncoder().encode(user.getPassword()));
        adminRepository.save(user);
    }
    @Override
    @Transactional
    public void removeUser(long id) {
        adminRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return adminRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(long id) {
        return adminRepository.findById(id).orElse(null);
    }
}
