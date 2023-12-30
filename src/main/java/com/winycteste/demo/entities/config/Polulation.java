package com.winycteste.demo.entities.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.winycteste.demo.entities.User;
import com.winycteste.demo.repository.PaymentRepository;
import com.winycteste.demo.repository.UserRepository;

@Configuration
public class Polulation implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public void run(String... args) throws Exception {

        User u1 = new User(null, "Winyc", "Winycios@gmail.com", "123");
        User u2 = new User(null, "OutroNome", "OutroEmail@gmail.com", "456");
        User u3 = new User(null, "MaisUmNome", "MaisUmEmail@gmail.com", "789");
        User u4 = new User(null, "NovoNome", "NovoEmail@gmail.com", "012");

        userRepository.saveAll(Arrays.asList(u1, u2, u3, u4));

        Payment p1 = new Payment(null, Instant.parse("2019-06-20T19:53:07Z"), u1);
        Payment p2 = new Payment(null, Instant.parse("2019-07-21T03:42:10Z"), u2);
        Payment p3 = new Payment(null, Instant.parse("2019-07-22T15:21:22Z"), u3);
        Payment p4 = new Payment(null, Instant.parse("2019-07-22T15:21:22Z"), u1);

        paymentRepository.saveAll(Arrays.asList(p1, p2, p3, p4));

    }
}
