package com.examplesoft.ecommercemonolite.domain.payment.repo;

import com.examplesoft.ecommercemonolite.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}
