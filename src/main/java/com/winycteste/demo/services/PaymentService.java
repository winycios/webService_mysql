package com.winycteste.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.winycteste.demo.entities.Payment;
import com.winycteste.demo.repository.PaymentRepository;
import com.winycteste.demo.services.exceptions.DatabaseException;
import com.winycteste.demo.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // Trazer todos os pagamentos
    public List<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }

    // Trazer pagamento por cliente
    public List<Payment> findAllPaymentsClient(Long clientId) {
        try {

            List<Payment> list = paymentRepository.findAllByUser_id(clientId);

            if (list.isEmpty()) {
                throw new ResourceNotFoundException(clientId);
            }
            return list;
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(clientId);
        }
    }

    // insert
    public Payment insert(Payment payment) {
        try {
            return paymentRepository.save(payment);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getCause().getMessage());
        }
    }

    // deletar
    public void deletar(Long id) {
        try {
            if (paymentRepository.findById(id).isEmpty()) {
                throw new ResourceNotFoundException(id);
            }

            paymentRepository.deleteById(id);

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
