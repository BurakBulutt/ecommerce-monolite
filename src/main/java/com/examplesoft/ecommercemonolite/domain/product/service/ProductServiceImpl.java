package com.examplesoft.ecommercemonolite.domain.product.service;

import com.examplesoft.ecommercemonolite.domain.category.dto.CategoryDto;
import com.examplesoft.ecommercemonolite.domain.category.dto.CategoryTreeDto;
import com.examplesoft.ecommercemonolite.domain.category.service.CategoryServiceImpl;
import com.examplesoft.ecommercemonolite.domain.product.dto.ProductDto;
import com.examplesoft.ecommercemonolite.domain.product.dto.ProductMapper;
import com.examplesoft.ecommercemonolite.domain.product.entity.Product;
import com.examplesoft.ecommercemonolite.domain.product.repo.ProductRepository;
import com.examplesoft.ecommercemonolite.util.BaseException;
import com.examplesoft.ecommercemonolite.util.MessageUtil;
import com.examplesoft.ecommercemonolite.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final CategoryServiceImpl categoryService;

    @Override
    public Page<ProductDto> getAll(Pageable pageable) {
        return PageUtil.toPage(repository.findAll(pageable),product -> {
            CategoryDto mainCategory = categoryService.getById(product.getMainCategoryId());
            return ProductMapper.toDto(product, mainCategory);
        });
    }

    @Override
    public Page<ProductDto> filter(String categorySlug, Pageable pageable) {
        List<Product> allProducts = new ArrayList<>();

        if (categorySlug != null) {
            CategoryDto categoryDto = categoryService.getBySlug(categorySlug);
            CategoryTreeDto categoryTreeDto = categoryService.getCategoryTree(categoryDto, new ArrayList<>());

            getProductsFromCategory(categoryTreeDto, allProducts);
        }

        if (categorySlug == null || allProducts.isEmpty()) {
            allProducts.addAll(repository.findAll());
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allProducts.size());
        List<Product> productsPage = allProducts.subList(start, end);

        List<ProductDto> productDtos = productsPage.stream().map(product -> {
            CategoryDto mainCategory = categoryService.getById(product.getMainCategoryId());
            return ProductMapper.toDto(product, mainCategory);
        }).toList();

        return new PageImpl<>(productDtos, pageable, allProducts.size());
    }

    private void getProductsFromCategory(CategoryTreeDto dto,List<Product> productList){
        repository.findByMainCategoryId(dto.getId()).ifPresent(productList::add);

        dto.getChildrensList().forEach(child -> {
            getProductsFromCategory(child,productList);
        });
    }

    @Override
    public ProductDto getById(String id) {
        return repository.findById(id).map(product -> {
            CategoryDto mainCategory = categoryService.getById(product.getMainCategoryId());
            return ProductMapper.toDto(product, mainCategory);
        }).orElseThrow(()-> new BaseException(MessageUtil.ENTITY_NOT_FOUND, Product.class.getSimpleName(),id));
    }

    @Override
    public ProductDto getBySlug(String slug) {
        return repository.findBySlug(slug).map(product -> {
            CategoryDto mainCategory = categoryService.getById(product.getMainCategoryId());
            return ProductMapper.toDto(product, mainCategory);
        }).orElseThrow(()-> new BaseException(MessageUtil.ENTITY_NOT_FOUND, Product.class.getSimpleName(),slug));
    }

    @Override
    @Transactional
    public ProductDto save(ProductDto dto) {
        CategoryDto mainCategory = categoryService.getById(dto.getMainCategory().getId());
        return ProductMapper.toDto(repository.save(ProductMapper.toEntity(new Product(), dto)), mainCategory);
    }

    @Override
    @Transactional
    public ProductDto update(String id, ProductDto dto) {
        Product product = repository.findById(id).orElseThrow(()-> new BaseException(MessageUtil.ENTITY_NOT_FOUND, Product.class.getSimpleName(),id));
        CategoryDto mainCategory = categoryService.getById(product.getMainCategoryId());
        return ProductMapper.toDto(repository.save(ProductMapper.toEntity(product, dto)),mainCategory);
    }

    @Override
    @Transactional
    public void delete(String id) {
        Product product = repository.findById(id).orElseThrow(()-> new BaseException(MessageUtil.ENTITY_NOT_FOUND, Product.class.getSimpleName(),id));
        repository.delete(product);
    }

    @EventListener
    public void updateProductStock(ProductStockUpdateEvent event) {
        Product product = repository.findById(event.productId()).orElseThrow(()-> new BaseException(MessageUtil.ENTITY_NOT_FOUND, Product.class.getSimpleName(),event.productId()));
        product.setQuantity(product.getQuantity() -1);
        repository.save(product);
    }
}
