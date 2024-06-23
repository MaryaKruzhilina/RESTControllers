package ru.maryKr.bootCrud.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.maryKr.bootCrud.model.Role;
import ru.maryKr.bootCrud.model.User;
import ru.maryKr.bootCrud.model.UserRole;
import ru.maryKr.bootCrud.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String getStartPage() {
        return "/welcome";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String getUserPage(@AuthenticationPrincipal UserDetails userDetails, ModelMap model) {
        model.addAttribute("user", service.findByUsername(userDetails.getUsername()));
        return "/user";
    }

    @GetMapping("/table")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public String getTable(ModelMap model) {
        List<User> users = service.getUsers();
        if (users.size() == 0) {
            return "redirect:/add_user";
        } else {
            model.addAttribute("users_list", users);
            return "table";
        }
    }
    @GetMapping("/update_user")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String update(@RequestParam("id") long id, Model model) {
        model.addAttribute("user", service.getUser(id));
        model.addAttribute("id", id);
        model.addAttribute("userRoles", UserRole.values());
        return "update_user";
    }
    @PostMapping("/update_user/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String edit(@ModelAttribute User user,
                       @RequestParam("id") long id,
                       @RequestParam(name = "uRoles", required = false) String[] userRoles,
                       ModelMap model) {
        Set<Role> roles = new HashSet<>();
        if(userRoles != null) {
            for(String ur : userRoles) {
                UserRole urRole = UserRole.valueOf(ur);
                Role role = new Role();
                role.setUserName(urRole);
                roles.add(role);
            }
        }
        if(roles.isEmpty()) {
            model.addAttribute("user", user);
            model.addAttribute("id", id);
            model.addAttribute("userRoles", UserRole.values());
            model.addAttribute("notRolesError", "Выберите роль");
            return "update_user";
        }

        user.setRoles(roles);

        if(user.getName().isEmpty() || service.isUsernameUnique(user.getName())) {
            model.addAttribute("user", user);
            model.addAttribute("id", id);
            model.addAttribute("userRoles", UserRole.values());
            if(user.getName().isEmpty()) {
                model.addAttribute("noLogin", "Заполните имя пользователя");
            } else {
                if (user.getId() != service.findByUsername(user.getName()).getId()) {
                    model.addAttribute("error", "Имя пользователя занято");
                }
            }
            return "update_user";
        }

        service.updateUser(id, user);
        return "redirect:/table";
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String edit(@RequestParam("id") long id) {
        System.out.println(id);
        service.removeUser(id);
        return "redirect:/table";
    }
    @GetMapping("/add_user")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("userRoles", UserRole.values());
        return "add_user";
    }
    @PostMapping("/add_user/add")
    public String addNewUser(@ModelAttribute("user") User user,
                             @RequestParam(name = "uRoles", required = false) String[] userRoles, ModelMap model) {

        Set<Role> roles = new HashSet<>();
        if(userRoles != null) {
            for(String ur : userRoles) {
                UserRole urRole = UserRole.valueOf(ur);
                Role role = new Role();
                role.setUserName(urRole);
                roles.add(role);
            }
        }
        if(roles.isEmpty()) {
            model.addAttribute("user", user);
            model.addAttribute("userRoles", UserRole.values());
            model.addAttribute("notRolesError", "Выберите роль");
            return "add_user";
        }

        user.setRoles(roles);

        if(user.getName().isEmpty() || user.getPassword().isEmpty() || service.isUsernameUnique(user.getName())) {
            model.addAttribute("user", user);
            model.addAttribute("userRoles", UserRole.values());
            if(user.getName().isEmpty()|| user.getPassword().isEmpty()) {
                model.addAttribute("noLoginPassword", "Заполните имя пользователя и пароль");
            } else {
                model.addAttribute("error", "Имя пользователя занято");
            }
            return "add_user";
        }

        service.addUser(user);
        return "redirect:/table";
    }

}
