package com.winycteste.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.winycteste.demo.entities.config.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Essa query se baseia ela retorna todos os pagamentos daquele usuario.
    // A Questão é a seguinte user(payment) e id(user)
    // user é o nome do atributo referente a classe User na payment. e o id é o proprio id do user
    List<Payment> findAllByUser_id(Long clientId);
}
