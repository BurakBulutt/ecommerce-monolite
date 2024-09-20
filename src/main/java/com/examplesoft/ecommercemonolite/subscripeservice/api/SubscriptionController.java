package com.examplesoft.ecommercemonolite.subscripeservice.api;

import com.examplesoft.ecommercemonolite.subscripeservice.entity.Subscription;
import com.examplesoft.ecommercemonolite.subscripeservice.service.SubscriptionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("notifications")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionServiceImpl service;

    @PostMapping("subscribe")
    public ResponseEntity<String> subscribe(@RequestBody Subscription subscription) {
        service.createOrUpdateSubscription(subscription);
        return new ResponseEntity<>("Subscription successful", HttpStatus.OK);
    }
}
