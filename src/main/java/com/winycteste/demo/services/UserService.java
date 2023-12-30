package com.winycteste.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winycteste.demo.entities.User;
import com.winycteste.demo.repository.UserRepository;
import com.winycteste.demo.services.exceptions.ValidationExc;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUser(String email) {
        List<User> Allusers = new ArrayList<User>();
        Allusers = findAllUsers();

        for (User x : Allusers) {

            if (x.getEmail().equals(email)) {

                return x;
            }
        }
        return null;
    }

    public User insert(User obj) {
        try {
            return userRepository.save(obj);
        } catch (ConstraintViolationException e) {
            throw new ValidationExc(
                    e.getConstraintViolations().stream().map(ConstraintViolation::getPropertyPath).toList().toString());
        }
    }
}
