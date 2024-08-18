package ru.maryKr.bootCrud.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.maryKr.bootCrud.controller.exception_handling.NoSuchUserException;
import ru.maryKr.bootCrud.controller.exception_handling.NotUniqueEmail;
import ru.maryKr.bootCrud.dto.UserDTO;
import ru.maryKr.bootCrud.model.User;
import ru.maryKr.bootCrud.service.AdminService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class RestUsersController {

    private final AdminService service;

    public RestUsersController(AdminService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<UserDTO> getUsers() {
        List<User> users = service.getUsers();
        return users.stream().map(UserDTO::new).collect(Collectors.toList());
    }
    @GetMapping("/users/{id}")
    public UserDTO getUser(@PathVariable int id) {
        User user = service.getUser(id);
        if(user == null){
            throw new NoSuchUserException("Пользователь с таким ID не найден");
        }
        UserDTO userDTO = new UserDTO(user);
        return userDTO;
    }
    @PostMapping("/users")
    public UserDTO createUser(@RequestBody User user) {
        if(service.isNotEmailUnique(user.getUsername())){
            throw new NotUniqueEmail("Пользователь с такой почтой уже зарегистрирован");
        }
        service.addUser(user);
        return new UserDTO(user);
    }
    @PutMapping("/users")
    public UserDTO updateUser(@RequestBody User user) {
        if(service.isNotEmailUnique(user.getUsername()) && !(user.getEmail().equals(service.getUser(user.getId()).getEmail()))){
            throw new NotUniqueEmail("Пользователь с такой почтой уже зарегистрирован");
        }
        service.addUser(user);
        return new UserDTO(user);
    }
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable int id) {
        User user = service.getUser(id);
        if(user == null){
            throw new NoSuchUserException("Пользователь с таким ID не найден");
        }
        service.removeUser(id);
        return "Пользователь удален";
    }
    @GetMapping("/currentUser")
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        UserDTO userDTO = new UserDTO(user);
        return ResponseEntity.ok(userDTO);
    }

}
