package com.examplesoft.ecommercemonolite.subscripeservice.repo;

import com.examplesoft.ecommercemonolite.subscripeservice.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, String> {
    Optional<Subscription> findByUserId(String userId);
}
