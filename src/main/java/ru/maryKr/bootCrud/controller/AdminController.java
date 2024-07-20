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
    public String update(@RequestParam(name = "id") Long id, Model model) {
        model.addAttribute("user", service.getUser(id));
        model.addAttribute("userRoles", UserRole.values());
        return "update_user";
    }

    @PostMapping("/admin/update_user/update")
    public String edit(@ModelAttribute("user")@Validated User user,
                       BindingResult bindingResult,
                       @RequestParam(name = "uRoles", required = false) String[] userRoles, ModelMap model) {
        Set<Role> roles = new HashSet<>();
        if(userRoles != null) {
            for(String ur : userRoles) {
                roles.add(new Role(UserRole.valueOf(ur)));
            }
        }
        if(service.isNotEmailUnique(user.getName()) && service.findByEmail(user.getEmail()).getId() != user.getId()) {
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

    @GetMapping("/admin/delete")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String edit(@RequestParam("id") long id) {
        service.removeUser(id);
        return "redirect:/admin";
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

//    @GetMapping("/add_user")
//    public String addUser(Model model) {
//        model.addAttribute("newUser", new User());
//        model.addAttribute("userRoles", UserRole.values());
//        return "add_user";
//    }

    @PostMapping("/add_user/add")
    public String addNewUser(@ModelAttribute("user") @Validated User user,
                             BindingResult bindingResult,
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
