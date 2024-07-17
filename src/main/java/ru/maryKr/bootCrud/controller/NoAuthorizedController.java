package ru.maryKr.bootCrud.controller;

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
@RequestMapping("/")
public class NoAuthorizedController {

    private final AdminService service;

    public NoAuthorizedController(AdminServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/")
    public String getStartPage() {
//        if(service.getUsers().size() == 0) {
//            User user = new User();
//            user.setEmail("admin@gmail.com");
//            user.setPassword("admin");
//            user.setName("admin");
//            Set<Role> userRoles = new HashSet<>();
//            userRoles.add(new Role(UserRole.ROLE_ADMIN));
//            user.setRoles(userRoles);
//            service.addUser(user);
//        }
        return "welcome";
    }
//    @GetMapping("/add_user")
//    public String addUser(Model model) {
//        model.addAttribute("user", new User());
//        model.addAttribute("userRoles", UserRole.values());
//        return "add_user";
//    }
//
//    @PostMapping("/add_user/add")
//    public String addNewUser(@ModelAttribute("user")@Validated User user,
//                             @AuthenticationPrincipal UserDetails userDetails,
//                             @RequestParam(name = "uRoles", required = false) String[] userRoles,
//                             BindingResult bindingResult, ModelMap model) {
//
//        Set<Role> roles = new HashSet<>();
//        if(userRoles != null) {
//            for(String ur : userRoles) {
//                UserRole urRole = UserRole.valueOf(ur);
//                Role role = new Role();
//                role.setUserRole(urRole);
//                roles.add(role);
//            }
//        }
//        if(service.isNotEmailUnique(user.getUsername())) {
//            model.addAttribute("users_list", service.getUsers());
//            model.addAttribute("user", user);
//            model.addAttribute("newUser", new User());
//            model.addAttribute("userRoles", UserRole.values());
//            model.addAttribute("notUnique", "Пользователь с такой почтой уже есть в базе");
//            return "index";
//        }
//        if(bindingResult.hasErrors()) {
//            model.addAttribute("users_list", service.getUsers());
//            model.addAttribute("user", user);
//            model.addAttribute("newUser", new User());
//            model.addAttribute("userRoles", UserRole.values());
//            model.addAttribute("errors", bindingResult.getAllErrors());
//            return "index";
//        }
//        if(roles.isEmpty()) {
//            model.addAttribute("user", user);
//            model.addAttribute("userRoles", UserRole.values());
//            model.addAttribute("notRolesError", "Выберите роль");
//            return "index";
//        }
//        user.setRoles(roles);
//        service.addUser(user);
//        return "index";
//    }
}
