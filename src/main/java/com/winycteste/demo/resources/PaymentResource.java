package com.winycteste.demo.resources;

import org.springframework.web.bind.annotation.RestController;

import com.winycteste.demo.entities.config.Payment;
import com.winycteste.demo.services.PaymentService;

import jakarta.annotation.Resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/pay")
@Resource
public class PaymentResource {

    @Autowired
    private PaymentService service;

    @GetMapping
    public ResponseEntity<List<Payment>> findAll() {
        List<Payment> result = service.findAllPayments();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "client/{id}")
    public ResponseEntity<List<Payment>> byClientID(@PathVariable Long id) {
        List<Payment> result = service.findAllPaymentsClient(id);
        return ResponseEntity.ok().body(result);
    }
}
