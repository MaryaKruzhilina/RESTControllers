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
import ru.maryKr.bootCrud.service.AdminService;
import ru.maryKr.bootCrud.service.AdminServiceImpl;

import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {

    private final AdminService service;

    public AdminController(AdminServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/admin/update_user")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String update(@RequestParam(name = "id") Long id,
                         @AuthenticationPrincipal UserDetails userDetails,
                         Model model) {
        User user = service.getUser(id);
        user.setPassword("");
        model.addAttribute("users_list", service.getUsers());
        model.addAttribute("userUpdate", user);
        model.addAttribute("user", service.findByEmail(userDetails.getUsername()));
        model.addAttribute("userRoles", UserRole.values());
        return "update";
    }

    @PostMapping("/admin/update_user/update")
    public String edit(@ModelAttribute("userUpdate") User user,
                       @AuthenticationPrincipal UserDetails userDetails,
                       @RequestParam(name = "uRoles", required = false) String[] userRoles, ModelMap model) {

        Set<Role> roles = new HashSet<>();
        if(userRoles != null) {
            for(String ur : userRoles) {
                roles.add(new Role(UserRole.valueOf(ur)));
            }
        }
        if(service.isNotEmailUnique(user.getUsername()) && !(user.getEmail().equals(service.getUser(user.getId()).getEmail()))) {
            user.setPassword("");
            model.addAttribute("users_list", service.getUsers());
            model.addAttribute("userUpdate", user);
            model.addAttribute("user", service.findByEmail(userDetails.getUsername()));
            model.addAttribute("userRoles", UserRole.values());
            model.addAttribute("errors", "Пользователь с такой почтой уже есть в базе");
            return "update";
        }

        user.setRoles(roles);
        service.addUser(user);
        return "redirect:/index/admin";
    }
    @GetMapping("/admin/delete")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String delete(@RequestParam(name = "id") Long id,
                         @AuthenticationPrincipal UserDetails userDetails,
                         Model model) {
        User user = service.getUser(id);
        user.setPassword("");
        model.addAttribute("users_list", service.getUsers());
        model.addAttribute("userDelete", user);
        model.addAttribute("user", service.findByEmail(userDetails.getUsername()));
        model.addAttribute("userRoles", UserRole.values());
        return "delete";
    }

    @PostMapping("/admin/delete/userDelete")
    public String deleteUser(@ModelAttribute("userDelete") User user) {
        service.removeUser(user.getId());
        return "redirect:/index/admin";
    }
    @GetMapping("/index/admin")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String getTable(@AuthenticationPrincipal UserDetails userDetails, ModelMap model) {
        model.addAttribute("users_list", service.getUsers());
        model.addAttribute("userRoles", UserRole.values());
        model.addAttribute("user", service.findByEmail(userDetails.getUsername()));
        model.addAttribute("newUser", new User());
        return "index";
    }

    @PostMapping("/add_user/add")
    public String addNewUser(@ModelAttribute("newUser") User user,
                             @AuthenticationPrincipal UserDetails userDetails,
                             @RequestParam(name = "uRoles", required = false) String[] userRoles,
                             ModelMap model) {

        Set<Role> roles = new HashSet<>();
        if(userRoles != null) {
            for(String ur : userRoles) {
                roles.add(new Role(UserRole.valueOf(ur)));
            }
        }
        if(service.isNotEmailUnique(user.getUsername())){
            model.addAttribute("users_list", service.getUsers());
            model.addAttribute("user", service.findByEmail(userDetails.getUsername()));
            model.addAttribute("newUser", user);
            model.addAttribute("userRoles", UserRole.values());
            model.addAttribute("errors", "Пользователь с такой почтой уже есть в базе");
            return "index";
        }

        user.setRoles(roles);
        service.addUser(user);
        return "redirect:/index/admin";
    }
}
