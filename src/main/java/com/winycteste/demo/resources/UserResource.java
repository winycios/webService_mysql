package com.winycteste.demo.resources;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.winycteste.demo.entities.User;
import com.winycteste.demo.services.UserService;

import com.winycteste.demo.services.exceptions.ResourceNotFoundException;

import jakarta.annotation.Resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
@Resource
public class UserResource {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> result = service.findAllUsers();
        return ResponseEntity.ok().body(result);
    }

    // Buscar por Usuario
    @GetMapping(value = "login/{email}")
    public ResponseEntity<User> buscarUser(@PathVariable String email) {
        User result = service.findUser(email);

        if (result != null) {
            return ResponseEntity.ok().body(result);
        }
        throw new ResourceNotFoundException(email);
    }

    // Cadastrar usuario
    @PostMapping
    public ResponseEntity<User> insert(@RequestBody User obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("users/login/{email}")
                .buildAndExpand(obj.getEmail()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

}
