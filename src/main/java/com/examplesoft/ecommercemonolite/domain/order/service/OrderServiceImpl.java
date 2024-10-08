package com.examplesoft.ecommercemonolite.domain.order.service;

import com.examplesoft.ecommercemonolite.domain.basket.dto.BasketDto;
import com.examplesoft.ecommercemonolite.domain.basket.service.BasketService;
import com.examplesoft.ecommercemonolite.domain.basket.service.ClearBasketEvent;
import com.examplesoft.ecommercemonolite.domain.order.dto.OrderDto;
import com.examplesoft.ecommercemonolite.domain.order.dto.OrderItemDto;
import com.examplesoft.ecommercemonolite.domain.order.dto.OrderMapper;
import com.examplesoft.ecommercemonolite.domain.order.entity.Order;
import com.examplesoft.ecommercemonolite.domain.order.entity.OrderItem;
import com.examplesoft.ecommercemonolite.domain.order.entity.OrderStatus;
import com.examplesoft.ecommercemonolite.domain.order.repo.OrderItemRepository;
import com.examplesoft.ecommercemonolite.domain.order.repo.OrderRepository;
import com.examplesoft.ecommercemonolite.domain.payment.api.PaymentSuccessEvent;
import com.examplesoft.ecommercemonolite.domain.payment.dto.PaymentDto;
import com.examplesoft.ecommercemonolite.domain.payment.service.PaymentServiceImpl;
import com.examplesoft.ecommercemonolite.domain.product.service.ProductService;
import com.examplesoft.ecommercemonolite.domain.product.dto.ProductStockUpdateEvent;
import com.examplesoft.ecommercemonolite.domain.user.dto.UserDto;
import com.examplesoft.ecommercemonolite.domain.user.service.UserService;
import com.examplesoft.ecommercemonolite.security.JwtUtil;
import com.examplesoft.ecommercemonolite.util.BaseException;
import com.examplesoft.ecommercemonolite.util.MessageUtil;
import com.examplesoft.ecommercemonolite.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final OrderItemRepository orderItemRepository;
    private final BasketService basketService;
    private final UserService userService;
    private final ProductService productService;
    private final ApplicationEventPublisher publisher;
    private final PaymentServiceImpl paymentService;

    @Override
    public Page<OrderDto> getAll(Pageable pageable) {
        return PageUtil.toPage(repository.findAll(pageable), order -> {
            List<OrderItemDto> orderItems = orderItemRepository.findAllByOrderId(order.getId()).stream()
                    .map(orderItem -> OrderMapper.toItemDto(orderItem, productService.getById(orderItem.getProductId())))
                    .toList();
            PaymentDto payment = paymentService.getById(order.getPaymentId());
            UserDto user = userService.getById(payment.getUser().getId());
            return OrderMapper.toDto(order,user,payment,orderItems);
        });
    }

    @Override
    public Page<OrderDto> filter(Pageable pageable) {
        return Page.empty();
    }

    @Override
    public OrderDto getOrderByCode(String code) {
        return repository.findByCode(code).map(order -> {
            List<OrderItemDto> orderItems = orderItemRepository.findAllByOrderId(order.getId()).stream()
                    .map(orderItem -> OrderMapper.toItemDto(orderItem, productService.getById(orderItem.getProductId())))
                    .toList();
            PaymentDto payment = paymentService.getById(order.getPaymentId());
            UserDto user = userService.getById(payment.getUser().getId());
            return OrderMapper.toDto(order, user,payment,orderItems);
        }).orElseThrow(() -> new BaseException(MessageUtil.ENTITY_NOT_FOUND, Order.class.getSimpleName(), code));
    }

    @Override
    public Page<OrderDto> getUserOrders(Pageable pageable) {
        return PageUtil.toPage(repository.findAllByUserId(JwtUtil.extractUserId(), pageable), order -> {
            List<OrderItemDto> orderItems = orderItemRepository.findAllByOrderId(order.getId()).stream()
                    .map(orderItem -> OrderMapper.toItemDto(orderItem, productService.getById(orderItem.getProductId())))
                    .toList();
            PaymentDto payment = paymentService.getById(order.getPaymentId());
            UserDto user = userService.getById(payment.getUser().getId());
            return OrderMapper.toDto(order,user,payment, orderItems);
        });
    }

    @EventListener
    @Transactional
    public OrderDto createOrder(PaymentSuccessEvent event) {
        BasketDto basket = basketService.getBasket();
        UserDto user = userService.getById(JwtUtil.extractUserId());

        Order order = new Order();
        order.setCode(UUID.randomUUID().toString());
        order.setAmount(basket.getTotalAmount());
        order.setStatus(OrderStatus.SIPARIS_ALINDI);
        order.setPaymentId(event.payment().getId());
        order.setUserId(user.getId());
        order.setDeliveryAddress(event.payment().getDeliveryAddress());
        order.setBillingAddress(event.payment().getBillingAddress());
        order.setShipmentDate(new Date());

        repository.save(order);

        List<OrderItemDto> orderItemDtos = new ArrayList<>();

        basket.getBasketProducts().forEach(basketProductDto -> {
            OrderItemDto orderItemDto = OrderMapper.toItemDto
                    (orderItemRepository.save(new OrderItem(order.getId(), basketProductDto.getProduct().getId(), basketProductDto.getQuantity(), basketProductDto.getTotalAmount())), productService.getById(basketProductDto.getProduct().getId()));
            orderItemDtos.add(orderItemDto);
            publisher.publishEvent(new ProductStockUpdateEvent(basketProductDto.getProduct().getId()));
        });
        publisher.publishEvent(new ClearBasketEvent(basket.getId()));
        return OrderMapper.toDto(order,user,event.payment(),orderItemDtos);
    }

    @Override
    @Transactional
    public OrderDto updateOrderStatus(String id, OrderStatus status) {
        Order order = repository.findById(id).orElseThrow(() -> new BaseException(MessageUtil.ENTITY_NOT_FOUND, Order.class.getSimpleName(), id));
        List<OrderItemDto> orderItemDtos = orderItemRepository.findAllByOrderId(order.getId()).stream()
                .map(orderItem -> OrderMapper.toItemDto(orderItem, productService.getById(orderItem.getProductId())))
                .toList();
        PaymentDto payment = paymentService.getById(order.getPaymentId());
        UserDto user = userService.getById(payment.getUser().getId());
        order.setStatus(status);
        return OrderMapper.toDto(repository.save(order),user,payment,orderItemDtos);
    }

    @Override
    @Transactional
    public void deleteOrder(String id) {
        Order order = repository.findById(id).orElseThrow(() -> new BaseException(MessageUtil.ENTITY_NOT_FOUND, Order.class.getSimpleName(), id));
        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(id);

        repository.delete(order);
        orderItemRepository.deleteAll(orderItems);
    }
}
