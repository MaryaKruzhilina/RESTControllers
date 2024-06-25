package ru.maryKr.bootCrud.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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

    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public String getTable(ModelMap model) {
        List<User> users = service.getUsers();
        if (users.size() == 0) {
            return "redirect:/add_user";
        } else {
            model.addAttribute("users_list", users);
            return "admin";
        }
    }
    @GetMapping("/update_user")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String update(@RequestParam(name = "id") Long id, Model model) {
        model.addAttribute("user", service.getUser(id));
        model.addAttribute("userRoles", UserRole.values());
        return "update_user";
    }

    @PostMapping("/update_user/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String edit(@ModelAttribute("user")@Validated User user,
                       BindingResult bindingResult,
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
        if(service.isNotUsernameUnique(user.getName()) && service.findByUsername(user.getName()).getId() != user.getId()) {
            model.addAttribute("user", user);
            model.addAttribute("userRoles", UserRole.values());
            model.addAttribute("notUnique", "Пользователь с таким именем уже есть в базе");
            return "update_user";
        }
        if(bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("userRoles", UserRole.values());
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "update_user";
        }

        if(roles.isEmpty()) {
            model.addAttribute("user", user);
            model.addAttribute("userRoles", UserRole.values());
            model.addAttribute("notRolesError", "Выберите роль");
            return "update_user";
        }


        user.setRoles(roles);

        service.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String edit(@RequestParam("id") long id) {
        service.removeUser(id);
        return "redirect:/admin";
    }
    @GetMapping("/add_user")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("userRoles", UserRole.values());
        return "add_user";
    }
    @PostMapping("/add_user/add")
    public String addNewUser(@ModelAttribute("user")@Validated User user,
                             BindingResult bindingResult,
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
        if(service.isNotUsernameUnique(user.getName())) {
            model.addAttribute("user", user);
            model.addAttribute("userRoles", UserRole.values());
            model.addAttribute("notUnique", "Пользователь с таким именем уже есть в базе");
            return "add_user";
        }
        if(bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("userRoles", UserRole.values());
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "add_user";
        }
        if(roles.isEmpty()) {
            model.addAttribute("user", user);
            model.addAttribute("userRoles", UserRole.values());
            model.addAttribute("notRolesError", "Выберите роль");
            return "add_user";
        }
        user.setRoles(roles);
        service.addUser(user);
        return "redirect:/admin";
    }

}
