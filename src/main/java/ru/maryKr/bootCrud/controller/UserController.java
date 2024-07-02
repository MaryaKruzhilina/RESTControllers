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
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }


    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String getUserPage(@AuthenticationPrincipal UserDetails userDetails, ModelMap model) {
        model.addAttribute("user", service.findByUsername(userDetails.getUsername()));
        return "/user";
    }


}
