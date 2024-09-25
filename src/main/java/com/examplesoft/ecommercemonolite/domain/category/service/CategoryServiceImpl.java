package com.examplesoft.ecommercemonolite.domain.category.service;

import com.examplesoft.ecommercemonolite.domain.category.dto.CategoryDto;
import com.examplesoft.ecommercemonolite.domain.category.dto.CategoryMapper;
import com.examplesoft.ecommercemonolite.domain.category.dto.CategoryTreeDto;
import com.examplesoft.ecommercemonolite.domain.category.entity.Category;
import com.examplesoft.ecommercemonolite.domain.category.repo.CategoryRepository;
import com.examplesoft.ecommercemonolite.domain.product.dto.CategoryDeleteEvent;
import com.examplesoft.ecommercemonolite.util.BaseException;
import com.examplesoft.ecommercemonolite.util.MessageUtil;
import com.examplesoft.ecommercemonolite.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final ApplicationEventPublisher publisher;

    @Override
    public Page<CategoryDto> getAll(Pageable pageable) {
        return PageUtil.toPage(repository.findAll(pageable), CategoryMapper::toDto);
    }

    @Override
    public Page<CategoryDto> filter(Pageable pageable) {
        return Page.empty();
    }

    @Override
    public CategoryDto getById(String id) {
        return repository.findById(id).map(CategoryMapper::toDto).orElseThrow(() -> new BaseException(MessageUtil.ENTITY_NOT_FOUND, Category.class.getSimpleName(), id));
    }

    @Override
    public CategoryDto getBySlug(String slug) {
        return repository.findBySlug(slug).map(CategoryMapper::toDto).orElseThrow(() -> new BaseException(MessageUtil.ENTITY_NOT_FOUND, Category.class.getSimpleName(), slug));

    }

    @Override
    @Transactional
    public CategoryDto save(CategoryDto dto) {
        if (dto.getParentId() != null && repository.findById(dto.getParentId()).isEmpty()) {
            throw new BaseException(MessageUtil.ENTITY_NOT_FOUND, Category.class.getSimpleName(), dto.getParentId());
        }
        return CategoryMapper.toDto(repository.save(CategoryMapper.toEntity(new Category(), dto)));
    }

    @Override
    @Transactional
    public CategoryDto update(String id, CategoryDto dto) {
        Category category = repository.findById(id).orElseThrow(() -> new BaseException(MessageUtil.ENTITY_NOT_FOUND, Category.class.getSimpleName(), id));
        if (category.getParentId() != null && repository.findById(category.getParentId()).isEmpty()) {
            throw new BaseException(MessageUtil.ENTITY_NOT_FOUND, Category.class.getSimpleName(), category.getParentId());
        }
        return CategoryMapper.toDto(repository.save(CategoryMapper.toEntity(category, dto)));
    }

    @Override
    @Transactional
    public void delete(String id) {
        Category category = repository.findById(id).orElseThrow(() -> new BaseException(MessageUtil.ENTITY_NOT_FOUND, Category.class.getSimpleName(), id));
        publisher.publishEvent(new CategoryDeleteEvent(category.getId()));
        repository.delete(category);
    }

    @Override
    public List<CategoryTreeDto> getMainCategoriesTree() {
        List<Category> categories = repository.findAll();
        List<CategoryDto> categoryDtos = categories.stream()
                .filter(category -> category.getParentId() == null)
                .map(CategoryMapper::toDto)
                .toList();

        List<CategoryTreeDto> categoryTree = new ArrayList<>();

        for (CategoryDto categoryDto : categoryDtos) {
            categoryTree.add(getCategoryTree(categoryDto,new ArrayList<>()));
        }

        return categoryTree;
    }

    public CategoryTreeDto getCategoryTree(CategoryDto mainCategory, List<CategoryTreeDto> categoryTree) {
        List<CategoryDto> categories = repository.findAllByParentId(mainCategory.getId()).stream()
                .map(CategoryMapper::toDto)
                .toList();

        if (!categories.isEmpty()) {
            for (CategoryDto categoryDto : categories) {
                categoryTree.add(getCategoryTree(categoryDto,new ArrayList<>()));
            }
        }

        return CategoryTreeDto.builder()
                .id(mainCategory.getId())
                .name(mainCategory.getName())
                .description(mainCategory.getDescription())
                .slug(mainCategory.getSlug())
                .parentId(mainCategory.getParentId())
                .childrensList(categoryTree)
                .build();
    }


}
