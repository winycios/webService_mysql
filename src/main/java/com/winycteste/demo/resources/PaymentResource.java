package com.winycteste.demo.resources;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.winycteste.demo.entities.Payment;
import com.winycteste.demo.services.PaymentService;

import jakarta.annotation.Resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/pay")
@Resource
public class PaymentResource {

    @Autowired
    private PaymentService service;

    // Trazer todos os pagamentos
    @GetMapping
    public ResponseEntity<List<Payment>> findAll() {
        List<Payment> result = service.findAllPayments();
        return ResponseEntity.ok().body(result);
    }

    // Trazer pagamento por cliente
    @GetMapping(value = "client/{id}")
    public ResponseEntity<List<Payment>> byClientID(@PathVariable Long id) {
        
        List<Payment> result = service.findAllPaymentsClient(id);
        return ResponseEntity.ok().body(result);
    }

    // insert
    @PostMapping
    public ResponseEntity<Payment> insert(@RequestBody Payment payment) {
        payment = service.insert(payment);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("payment/client/{id}")
                .buildAndExpand(payment.getUser().getEmail()).toUri();
        return ResponseEntity.created(uri).body(payment);
    }


      // Deletar
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.ok().build();
    }
}
