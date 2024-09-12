package com.examplesoft.ecommercemonolite.domain.basket.service;

import com.examplesoft.ecommercemonolite.domain.basket.api.AddProductRequest;
import com.examplesoft.ecommercemonolite.domain.basket.dto.BasketDto;
import com.examplesoft.ecommercemonolite.domain.basket.dto.BasketProductDto;
import com.examplesoft.ecommercemonolite.domain.basket.entity.Basket;
import com.examplesoft.ecommercemonolite.domain.basket.entity.BasketProduct;
import com.examplesoft.ecommercemonolite.domain.basket.repo.BasketProductRepository;
import com.examplesoft.ecommercemonolite.domain.basket.repo.BasketRepository;
import com.examplesoft.ecommercemonolite.domain.product.dto.ProductDto;
import com.examplesoft.ecommercemonolite.domain.product.service.ProductService;
import com.examplesoft.ecommercemonolite.domain.user.dto.UserDto;
import com.examplesoft.ecommercemonolite.domain.user.service.UserService;
import com.examplesoft.ecommercemonolite.security.JwtUtil;
import com.examplesoft.ecommercemonolite.util.BaseException;
import com.examplesoft.ecommercemonolite.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;
    private final BasketProductRepository basketProductRepository;
    private final ProductService productService;
    private final UserService userService;

    @Override
    public BasketDto getBasket() {
        if(JwtUtil.extractUserId().equals("UNKNOWN_USER")){
            throw new BaseException(MessageUtil.FAIL);
        }
        UserDto user = userService.getById(JwtUtil.extractUserId());
        return basketRepository.findByUserId(user.getId()).map(basket -> {
            AtomicReference<BigDecimal> amount = new AtomicReference<>(BigDecimal.ZERO);
            List<BasketProductDto> basketProducts = basketProductRepository.findAllByBasketId(basket.getId()).stream()
                    .map(basketProduct -> {
                                amount.set(amount.get().add(basketProduct.getTotalAmount()));
                                return toBasketProductDto(basket, basketProduct);
                            }
                    )
                    .toList();
            basket.setTotalAmount(amount.get());
            return toDto(basketRepository.save(basket), basketProducts);
        }).orElseThrow(() -> new BaseException(MessageUtil.BASKET_NOT_FOUND));
    }

    @Override
    @Transactional
    public BasketDto addProductToBasket(AddProductRequest request) {
        BasketDto basket = getBasket();
        ProductDto product = productService.getById(request.productId());

        if (request.quantity() > product.getQuantity()) {
            throw new BaseException(MessageUtil.FAIL);
        }

        BasketProductDto basketProduct = basket.getBasketProducts().stream()
                .filter(basketProductDto -> basketProductDto.getProduct().getId().equals(request.productId()))
                .findFirst().orElse(null);

        final BasketProduct bProduct;

        if (basketProduct == null) {
            BigDecimal amount = product.getPrice().multiply(new BigDecimal(request.quantity()));
            bProduct = new BasketProduct(basket.getId(), product.getId(), request.quantity(), amount);
            basketProductRepository.save(bProduct);
        } else {
            bProduct = basketProductRepository.findById(basketProduct.getId()).orElseThrow(() -> new BaseException(MessageUtil.FAIL));

            int newQuantity = bProduct.getQuantity() + request.quantity();

            if (newQuantity > product.getQuantity()) {
                throw new BaseException(MessageUtil.FAIL);
            }

            bProduct.setQuantity(newQuantity);
            bProduct.setTotalAmount(new BigDecimal(newQuantity).multiply(product.getPrice()));
            basketProductRepository.save(bProduct);
        }

        return getBasket();
    }

    @Override
    @Transactional
    public BasketDto changeQuantity(String basketProductId, Integer quantity) {
        BasketProduct basketProduct = basketProductRepository.findById(basketProductId).orElseThrow(() -> new BaseException(MessageUtil.FAIL));
        ProductDto product = productService.getById(basketProduct.getProductId());

        if (quantity > product.getQuantity()) {
            throw new BaseException(MessageUtil.FAIL);
        }

        if (quantity <= 0) {
            basketProductRepository.delete(basketProduct);
        } else {
            basketProduct.setQuantity(quantity);
            basketProduct.setTotalAmount(new BigDecimal(quantity).multiply(product.getPrice()));
            basketProductRepository.save(basketProduct);
        }

        return getBasket();
    }

    @Override
    @Transactional
    public void clearBasket() {
        Basket basket = basketRepository.findByUserId(JwtUtil.extractUserId()).orElseThrow(() -> new BaseException(MessageUtil.BASKET_NOT_FOUND));
        List<BasketProduct> basketProducts = basketProductRepository.findAllByBasketId(JwtUtil.extractUserId());
        basketProductRepository.deleteAll(basketProducts);
        basket.setTotalAmount(BigDecimal.ZERO);
        basketRepository.save(basket);
    }

    @Override
    @Transactional
    public BasketDto deleteBasketProduct(String basketProductId) {
        BasketDto basket = getBasket();

        if (basket.getBasketProducts().stream().anyMatch(productDto -> productDto.getId().equals(basketProductId))) {
            basketProductRepository.deleteById(basketProductId);
        }else {
            throw new BaseException(MessageUtil.FAIL);
        }

        return getBasket();
    }

    @EventListener
    @Transactional
    public void clearBasketEvent(ClearBasketEvent event){
        Basket basket = basketRepository.findById(event.basketId()).orElseThrow(() -> new BaseException(MessageUtil.BASKET_NOT_FOUND));
        List<BasketProduct> basketProducts = basketProductRepository.findAllByBasketId(basket.getId());
        basketProductRepository.deleteAll(basketProducts);
        basket.setTotalAmount(BigDecimal.ZERO);
        basketRepository.save(basket);
    }

    @EventListener
    public void createBasketForUserEvent(UserBasketCreationEvent event) {
        basketRepository.save(new Basket(event.userId(), BigDecimal.ZERO));
    }

    private BasketProductDto toBasketProductDto(Basket basket, BasketProduct basketProduct) {
        return BasketProductDto.builder()
                .id(basketProduct.getId())
                .product(productService.getById(basketProduct.getProductId()))
                .basketId(basket.getId())
                .totalAmount(basketProduct.getTotalAmount())
                .quantity(basketProduct.getQuantity())
                .build();
    }

    private BasketDto toDto(Basket entity, List<BasketProductDto> basketProducts) {
        return BasketDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .totalAmount(entity.getTotalAmount())
                .basketProducts(basketProducts)
                .build();
    }
}
