package com.winycteste.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.winycteste.demo.entities.User;
import com.winycteste.demo.repository.UserRepository;
import com.winycteste.demo.services.exceptions.AuthException;
import com.winycteste.demo.services.exceptions.DatabaseException;
import com.winycteste.demo.services.exceptions.ResourceNotFoundException;
import com.winycteste.demo.services.exceptions.ValidationExc;

import jakarta.persistence.EntityNotFoundException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // todos os usuarios
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // usuario por email
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

    // Autenticar usuario
    public User auth(String email, String password) {
        List<User> user = userRepository.findAll().stream()
                .filter(c -> c.getEmail().equals(email) && c.getPassword().equals(password)).toList();

        if (!user.isEmpty()) {
            for (User auth : user) {
                return new User(auth.getId(), auth.getUsername(), auth.getEmail(), auth.getPassword());
            }
        }
        throw new AuthException("User not found");
    }

    // inserir usuario
    public User insert(User obj) {
        try {
            User exists = findUser(obj.getEmail());
            if (exists != null) {
                throw new AuthException("User already exists");
            }

            return userRepository.save(obj);
        } catch (ConstraintViolationException e) {
            throw new ValidationExc(
                    e.getConstraintViolations().stream().map(ConstraintViolation::getPropertyPath).toList().toString());
        }
    }

    // deletar usuario
    public void deletar(Long id) {
        try {
            if (userRepository.findById(id).isEmpty()) {
                throw new ResourceNotFoundException(id);
            }

            userRepository.deleteById(id);

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    // atualizar dados
    public User updatUser(Long id, User obj) {
        try {
            User entity = userRepository.getReferenceById(id);

            // validação manual
            // https://reflectoring-io.translate.goog/bean-validation-with-spring-boot/?_x_tr_sl=auto&_x_tr_tl=pt&_x_tr_hl=pt-BR
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<User>> violations = validator.validate(obj);
            if (!violations.isEmpty()) {
                throw new ValidationExc(
                        violations.stream().map(ConstraintViolation::getMessageTemplate).toList().toString());

            }

            updateData(entity, obj);
            return userRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(User entity, User obj) {
        entity.setUsername(obj.getUsername());
        entity.setEmail(obj.getEmail());
        entity.setPassword(obj.getPassword());
    }

}
