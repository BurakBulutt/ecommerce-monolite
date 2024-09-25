package com.examplesoft.ecommercemonolite.domain.payment.service;

import com.examplesoft.ecommercemonolite.domain.basket.dto.BasketDto;
import com.examplesoft.ecommercemonolite.domain.basket.service.BasketService;
import com.examplesoft.ecommercemonolite.domain.payment.api.PaymentSuccessEvent;
import com.examplesoft.ecommercemonolite.domain.payment.dto.PaymentDto;
import com.examplesoft.ecommercemonolite.domain.payment.entity.Payment;
import com.examplesoft.ecommercemonolite.domain.payment.repo.PaymentRepository;
import com.examplesoft.ecommercemonolite.domain.product.service.ProductService;
import com.examplesoft.ecommercemonolite.domain.user.dto.UserDto;
import com.examplesoft.ecommercemonolite.domain.user.service.UserService;
import com.examplesoft.ecommercemonolite.security.JwtUtil;
import com.examplesoft.ecommercemonolite.util.BaseException;
import com.examplesoft.ecommercemonolite.util.MessageUtil;
import com.examplesoft.ecommercemonolite.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl {
    private final PaymentRepository repository;
    private final BasketService basketService;
    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final ProductService productService;

    public Page<PaymentDto> getAll(Pageable pageable) {
        return PageUtil.toPage(repository.findAll(pageable),payment -> {
            UserDto user = userService.getById(payment.getUserId());
            return toDto(payment,user);
        });
    }

    public PaymentDto getById(String id) {
        return repository.findById(id).map(payment -> {
            UserDto user = userService.getById(payment.getUserId());
            return toDto(payment,user);
        }).orElseThrow(() -> new BaseException(MessageUtil.ENTITY_NOT_FOUND,Payment.class.getSimpleName(),id));
    }

    @Transactional
    public void createPayment(PaymentDto payment) {
        UserDto user = userService.getById(JwtUtil.extractUserId());
        BasketDto basket = basketService.getBasket();

        if (basket.getBasketProducts().isEmpty()) {
            throw new BaseException(MessageUtil.BASKET_EMPTY);
        }

        basket.getBasketProducts().forEach(basketProductDto -> {
            if (productService.getById(basketProductDto.getProduct().getId()).getQuantity() <= 0) {
                throw new BaseException(MessageUtil.NO_STOCK_PRODUCT, basketProductDto.getProduct().getId());
            }
        });

        payment.setPrice(basket.getTotalAmount());
        payment.setUser(user);

        Payment p = repository.save(toEntity(new Payment(), payment));
        PaymentDto paymentDto = toDto(p,user);

        publisher.publishEvent(new PaymentSuccessEvent(paymentDto));
    }


    private PaymentDto toDto(Payment payment,UserDto user) {
        return PaymentDto.builder()
                .id(payment.getId())
                .deliveryAddress(payment.getDeliveryAddress())
                .billingAddress(payment.getBillingAddress())
                .price(payment.getPrice())
                .user(user)
                .build();
    }

    private Payment toEntity(Payment payment,PaymentDto dto) {
        payment.setDeliveryAddress(dto.getDeliveryAddress());
        payment.setBillingAddress(dto.getBillingAddress());
        payment.setPrice(dto.getPrice());
        payment.setUserId(dto.getUser().getId());

        return payment;
    }
}
