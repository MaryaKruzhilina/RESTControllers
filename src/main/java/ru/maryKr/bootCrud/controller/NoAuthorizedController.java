package ru.maryKr.bootCrud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.maryKr.bootCrud.service.AdminService;
import ru.maryKr.bootCrud.service.AdminServiceImpl;

@Controller
@RequestMapping("/")
public class NoAuthorizedController {

    private final AdminService service;

    public NoAuthorizedController(AdminServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String getStartPage() {
        return "login";
    }
}
