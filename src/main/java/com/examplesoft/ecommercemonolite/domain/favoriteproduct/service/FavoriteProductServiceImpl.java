package com.examplesoft.ecommercemonolite.domain.favoriteproduct.service;

import com.examplesoft.ecommercemonolite.domain.favoriteproduct.dto.FavoriteProductDto;
import com.examplesoft.ecommercemonolite.domain.favoriteproduct.dto.FavoriteProductMapper;
import com.examplesoft.ecommercemonolite.domain.favoriteproduct.entity.FavoriteProduct;
import com.examplesoft.ecommercemonolite.domain.favoriteproduct.repo.FavoriteProductRepository;
import com.examplesoft.ecommercemonolite.domain.product.dto.ProductDto;
import com.examplesoft.ecommercemonolite.domain.product.service.ProductService;
import com.examplesoft.ecommercemonolite.domain.user.dto.UserDto;
import com.examplesoft.ecommercemonolite.domain.user.service.UserService;
import com.examplesoft.ecommercemonolite.security.JwtUtil;
import com.examplesoft.ecommercemonolite.util.BaseException;
import com.examplesoft.ecommercemonolite.util.MessageUtil;
import com.examplesoft.ecommercemonolite.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FavoriteProductServiceImpl implements FavoriteProductService {
    private final FavoriteProductRepository repository;
    private final UserService userService;
    private final ProductService productService;

    @Override
    public Page<FavoriteProductDto> getUserFavoriteProducts(Pageable pageable) {
        UserDto user = userService.getById(JwtUtil.extractUserId());
        return PageUtil.toPage(repository.findAllByUserId(user.getId(), pageable), favoriteProduct -> {
            ProductDto product = productService.getById(favoriteProduct.getProductId());
            return FavoriteProductMapper.toDto(favoriteProduct, user, product);
        });
    }

    @Override
    public List<FavoriteProductDto> getUserFavoriteProducts(String userId) {
        UserDto user = userService.getById(userId);
        return repository.findAllByUserId(user.getId()).stream()
                .map(favoriteProduct -> {
                    ProductDto product = productService.getById(favoriteProduct.getProductId());
                    return FavoriteProductMapper.toDto(favoriteProduct, user, product);
                }).toList();
    }

    @Override
    public FavoriteProductDto isFavoriteProduct(String productId) {
        return repository.findByUserIdAndProductId(JwtUtil.extractUserId(), productId)
                .map(favoriteProduct -> FavoriteProductMapper.toDto(favoriteProduct,null,null)).orElseThrow(()-> new BaseException(MessageUtil.ENTITY_NOT_FOUND));
    }

    @Override
    @Transactional
    public FavoriteProductDto save(String productId) {
        ProductDto product = productService.getById(productId);
        UserDto user = userService.getById(JwtUtil.extractUserId());
        return FavoriteProductMapper.toDto(repository.save(FavoriteProductMapper.toEntity(new FavoriteProduct(), FavoriteProductDto.builder()
                .product(product)
                .user(user)
                .build())), user, product);
    }

    @Override
    @Transactional
    public void delete(String id) {
        FavoriteProduct favoriteProduct = repository.findById(id).orElseThrow(() -> new BaseException(MessageUtil.ENTITY_NOT_FOUND,FavoriteProduct.class.getSimpleName(), id));
        repository.delete(favoriteProduct);
    }
}
