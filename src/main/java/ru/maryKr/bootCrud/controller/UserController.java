package ru.maryKr.bootCrud.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.maryKr.bootCrud.model.User;
import ru.maryKr.bootCrud.model.UserRole;
import ru.maryKr.bootCrud.service.UserService;
import ru.maryKr.bootCrud.service.UserServiceImpl;

@Controller
public class UserController {

    private final UserService service;

    public UserController(UserServiceImpl service) {
        this.service = service;
    }


    @GetMapping("/index")
    public String getUserPage( ModelMap model) {
        model.addAttribute("userRoles", UserRole.values());
        model.addAttribute("newUser", new User());
        return "/index";
    }


}
