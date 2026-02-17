package org.kfe.api.demo.controller;

import org.kfe.api.demo.dto.UpdateUserRequestDTO;
import org.kfe.api.demo.entity.User;
import org.kfe.api.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    // GET /api/users?name=Juan

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String name) {
        return ResponseEntity.ok(userService.searchByName(name));
    }


    //  GET /api/users

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }


    //  PUT /api/users/{id}

    @PutMapping("/{id}")
    public ResponseEntity<User> update(
            @PathVariable Long id,
            @RequestBody UpdateUserRequestDTO request) {

        return ResponseEntity.ok(userService.update(id, request));
    }


    //  DELETE /api/users/{id}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}