package com.winycteste.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winycteste.demo.entities.config.Payment;
import com.winycteste.demo.repository.PaymentRepository;
import com.winycteste.demo.services.exceptions.ResourceNotFoundException;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }

    public List<Payment> findAllPaymentsClient(Long clientId) {
        List<Payment> list = paymentRepository.findAllByUser_id(clientId);

        if (list.isEmpty()) {
            throw new ResourceNotFoundException(clientId);
        }
        return list;
    }

}
