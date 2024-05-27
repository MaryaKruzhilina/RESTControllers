package ru.maryKr.bootCrud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.maryKr.bootCrud.model.User;
import ru.maryKr.bootCrud.service.UserService;

import java.util.List;

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
    @GetMapping("/table")
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
    public String update(@RequestParam("id") long id, Model model) {
        System.out.println(id);
        model.addAttribute("user", service.getUser(id));
        model.addAttribute("id", id);
        return "update_user";
    }
    @PostMapping("/update_user/update")
    public String edit(@RequestParam("id") long id, @ModelAttribute User user) {
        service.updateUser(id, user);
        return "redirect:/table";
    }
    @GetMapping("/delete")
    public String edit(@RequestParam("id") long id) {
        System.out.println(id);
        service.removeUser(id);
        return "redirect:/table";
    }
    @GetMapping("/add_user")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "add_user";
    }
    @PostMapping("/add_user/add")
    public String addNewUser(@ModelAttribute("user") User user) {
        service.addUser(user);
        return "redirect:/table";
    }


}
