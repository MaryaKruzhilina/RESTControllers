package ru.maryKr.bootCrud.testUsers;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import ru.maryKr.bootCrud.model.Role;
import ru.maryKr.bootCrud.model.User;
import ru.maryKr.bootCrud.model.UserRole;
import ru.maryKr.bootCrud.service.AdminService;

import java.util.HashSet;
import java.util.Set;

@Component
public class FirstUsers {

    private final AdminService service;

    public FirstUsers(AdminService service) {
        this.service = service;
    }
    @PostConstruct
    public void init() {
        if(service.getUsers().size() == 0){
            Set<Role> rolesAdmin = new HashSet<>();
            rolesAdmin.add(new Role(UserRole.ROLE_ADMIN));
            User userAdmin = new User("admin", "admin", rolesAdmin, "Иванов", "admin@admin.com", 100);

            Set<Role> rolesUser = new HashSet<>();
            rolesUser.add(new Role(UserRole.ROLE_USER));
            User userUser = new User("user", "user", rolesUser, "Петров", "user@user.com", 5);

            service.addUser(userAdmin);
            service.addUser(userUser);
        }

    }

}
