package com.example.transactions.users;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user={username}")
    public ResponseEntity<UserEntity> getUser(@PathVariable String username) {
        UserEntity user = userService.getUserByUsername(username);

        return ResponseEntity
                .ok(user);
    }

    @PostMapping("/new_user")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        UserEntity userEntity = userService.createUser(user);

        return ResponseEntity
                .created(linkTo(methodOn(UserController.class)
                        .getUser(userEntity.getUsername())).toUri())
                .body(userEntity);
    }

    @PutMapping("/user={username}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable String username, @RequestBody UserEntity user) {
        UserEntity userEntity = userService.updateUser(username, user);

        return ResponseEntity
                .ok(userEntity);
    }

    @DeleteMapping("/user={username}")
    public ResponseEntity<UserEntity> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);

        return ResponseEntity.noContent().build();
    }
}
