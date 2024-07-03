package ru.maryKr.bootCrud.controller;

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
@RequestMapping("/")
public class NoAuthorizedController {

    private final AdminService service;

    public NoAuthorizedController(AdminServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/")
    public String getStartPage() {
        return "/welcome";
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
