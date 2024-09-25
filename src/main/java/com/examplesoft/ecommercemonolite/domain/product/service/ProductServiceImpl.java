package com.examplesoft.ecommercemonolite.domain.product.service;

import com.examplesoft.ecommercemonolite.domain.campaign.dto.CampaignDto;
import com.examplesoft.ecommercemonolite.domain.campaign.service.CampaignServiceImpl;
import com.examplesoft.ecommercemonolite.domain.category.dto.CategoryDto;
import com.examplesoft.ecommercemonolite.domain.category.dto.CategoryTreeDto;
import com.examplesoft.ecommercemonolite.domain.category.service.CategoryServiceImpl;
import com.examplesoft.ecommercemonolite.domain.product.dto.CategoryDeleteEvent;
import com.examplesoft.ecommercemonolite.domain.product.dto.ProductDto;
import com.examplesoft.ecommercemonolite.domain.product.dto.ProductMapper;
import com.examplesoft.ecommercemonolite.domain.product.dto.ProductStockUpdateEvent;
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
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final CategoryServiceImpl categoryService;
    private final CampaignServiceImpl campaignService;

    @Override
    public Page<ProductDto> getAll(Pageable pageable) {
        return PageUtil.toPage(repository.findAll(pageable), product -> {
            CategoryDto mainCategory = categoryService.getById(product.getMainCategoryId());
            List<CampaignDto> campaigns = campaignService.getAllByTargets(getTargets(product, mainCategory));
            return ProductMapper.toDto(product, mainCategory, campaigns);
        });
    }

    @Override
    public List<ProductDto> getAllByIdIn(Set<String> ids) {
        return repository.findAllById(ids).stream().map(product -> {
            CategoryDto mainCategory = categoryService.getById(product.getMainCategoryId());
            List<CampaignDto> campaigns = campaignService.getAllByTargets(getTargets(product, mainCategory));
            return ProductMapper.toDto(product, mainCategory, campaigns);
        }).toList();
    }

    private Set<String> getTargets(Product product, CategoryDto mainCategory) {
        Set<String> targets = new HashSet<>();
        targets.add(product.getId());

        if (mainCategory.getParentId() == null) {
            targets.add(product.getMainCategoryId());
        } else {
            Set<String> categoryTargets = new HashSet<>();
            String parent = mainCategory.getParentId();
            targets.add(parent);
            do {
                CategoryDto c = categoryService.getById(parent);
                categoryTargets.add(c.getId());
                parent = c.getParentId();
            } while (parent != null);
            targets.addAll(categoryTargets);
        }

        return targets;
    }

    @Override
    public Page<ProductDto> filter(String categorySlug, Pageable pageable) {
        List<Product> allProducts = new ArrayList<>();

        if (StringUtils.hasLength(categorySlug)) {
            CategoryDto categoryDto = categoryService.getBySlug(categorySlug);
            CategoryTreeDto categoryTreeDto = categoryService.getCategoryTree(categoryDto, new ArrayList<>());

            getProductsFromCategory(categoryTreeDto, allProducts);
        }

        if (categorySlug == null) {
            allProducts.addAll(repository.findAll());
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allProducts.size());
        List<Product> productsPage = allProducts.subList(start, end);

        List<ProductDto> productDtos = productsPage.stream().map(product -> {
            CategoryDto mainCategory = categoryService.getById(product.getMainCategoryId());
            List<CampaignDto> campaigns = campaignService.getAllByTargets(getTargets(product, mainCategory));
            return ProductMapper.toDto(product, mainCategory, campaigns);
        }).toList();

        return new PageImpl<>(productDtos, pageable, allProducts.size());
    }

    private void getProductsFromCategory(CategoryTreeDto dto, List<Product> allProducts) {
        allProducts.addAll(repository.findAllByMainCategoryId(dto.getId()));

        dto.getChildrensList().forEach(child -> getProductsFromCategory(child, allProducts));
    }

    @Override
    public Page<ProductDto> searchFilter(String keyword, Pageable pageable) {
        Page<Product> products = repository.findAllByNameContaining(keyword, pageable);
        return PageUtil.toPage(products, product -> {
            CategoryDto mainCategory = categoryService.getById(product.getMainCategoryId());
            List<CampaignDto> campaigns = campaignService.getAllByTargets(getTargets(product, mainCategory));
            return ProductMapper.toDto(product, mainCategory, campaigns);
        });
    }

    @Override
    public ProductDto getById(String id) {
        return repository.findById(id).map(product -> {
            CategoryDto mainCategory = categoryService.getById(product.getMainCategoryId());
            List<CampaignDto> campaigns = campaignService.getAllByTargets(getTargets(product, mainCategory));
            return ProductMapper.toDto(product, mainCategory, campaigns);
        }).orElseThrow(() -> new BaseException(MessageUtil.ENTITY_NOT_FOUND, Product.class.getSimpleName(), id));
    }

    @Override
    public ProductDto getBySlug(String slug) {
        return repository.findBySlug(slug).map(product -> {
            CategoryDto mainCategory = categoryService.getById(product.getMainCategoryId());
            List<CampaignDto> campaigns = campaignService.getAllByTargets(getTargets(product, mainCategory));
            return ProductMapper.toDto(product, mainCategory, campaigns);
        }).orElseThrow(() -> new BaseException(MessageUtil.ENTITY_NOT_FOUND, Product.class.getSimpleName(), slug));
    }

    @Override
    @Transactional
    public ProductDto save(ProductDto dto) {
        CategoryDto mainCategory = categoryService.getById(dto.getMainCategory().getId());
        return ProductMapper.toDto(repository.save(ProductMapper.toEntity(new Product(), dto)), mainCategory, new ArrayList<>());
    }

    @Override
    @Transactional
    public ProductDto update(String id, ProductDto dto) {
        Product product = repository.findById(id).orElseThrow(() -> new BaseException(MessageUtil.ENTITY_NOT_FOUND, Product.class.getSimpleName(), id));
        CategoryDto mainCategory = categoryService.getById(product.getMainCategoryId());
        List<CampaignDto> campaigns = campaignService.getAllByTargets(getTargets(product, mainCategory));
        return ProductMapper.toDto(repository.save(ProductMapper.toEntity(product, dto)), mainCategory, campaigns);
    }

    @Override
    @Transactional
    public void delete(String id) {
        Product product = repository.findById(id).orElseThrow(() -> new BaseException(MessageUtil.ENTITY_NOT_FOUND, Product.class.getSimpleName(), id));
        List<CampaignDto> campaigns = campaignService.getAllByTargets(Set.of(product.getId()));

        if (!campaigns.isEmpty()) {
            throw new BaseException(MessageUtil.FAIL);
        }
        repository.delete(product);
    }

    @EventListener
    public void categoryDeleteCheckEvent(CategoryDeleteEvent event){
        List<Product> products = repository.findAllByMainCategoryId(event.categoryId());
        if (!products.isEmpty()){
            throw new BaseException(MessageUtil.FAIL);
        }
    }

    @EventListener
    public void updateProductStock(ProductStockUpdateEvent event) {
        Product product = repository.findById(event.productId()).orElseThrow(() -> new BaseException(MessageUtil.ENTITY_NOT_FOUND, Product.class.getSimpleName(), event.productId()));
        product.setQuantity(product.getQuantity() - 1);
        repository.save(product);
    }
}
