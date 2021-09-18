package pdp.uz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdp.uz.entity.Category;
import pdp.uz.helpers.MapstructMapper;
import pdp.uz.helpers.Utils;
import pdp.uz.model.CategoryAddDto;
import pdp.uz.model.CategoryDto;
import pdp.uz.repository.CategoryRepo;
import pdp.uz.service.CategoryService;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final MapstructMapper mapstructMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepo categoryRepo, MapstructMapper mapstructMapper) {
        this.categoryRepo = categoryRepo;
        this.mapstructMapper = mapstructMapper;
    }

    @Override
    public List<CategoryDto> getParents() {
        List<Category> parents = categoryRepo.findAllParentCategories();
        return mapstructMapper.toCategoryDto(parents);
    }

    @Override
    public List<CategoryDto> getChildren(Long id) {
        Category parentCategory = validate(id);
        List<Category> children = categoryRepo.findAllChildren(parentCategory);
        return mapstructMapper.toCategoryDto(children);
    }

    @Override
    public CategoryDto add(CategoryAddDto dto) {
        String name = dto.getName();
        if (Utils.isEmpty(name)) {
            throw new RuntimeException("Category name should not be null!");
        } else {
            Optional<Category> categoryOpt = categoryRepo.findByName(name);
            if (categoryOpt.isPresent()) {
                throw new RuntimeException("This name is already exist!");
            }
        }

        Long parentCategoryId = dto.getParentCategoryId();
        Category parentCategory = null;

        if (!Utils.isEmpty(parentCategoryId)) {
            Optional<Category> parentCategoryOpt = categoryRepo.findById(parentCategoryId);
            if (!parentCategoryOpt.isPresent()) {
                throw new RuntimeException("Parent Category id = " + parentCategoryId + ", not found!");
            }
            parentCategory = parentCategoryOpt.get();
            if (!parentCategory.getActive()) {
                throw new RuntimeException("Parent Category id = " + parentCategoryId + ", is inactive!");
            }
        }

        Category category = mapstructMapper.toCategory(dto);
        category.setParentCategory(parentCategory);

        Category savedCategory = categoryRepo.save(category);

        return mapstructMapper.toCategoryDto(savedCategory);
    }

    @Override
    public Category validate(Long id) {
        Optional<Category> categoryOpt = categoryRepo.findById(id);
        if (!categoryOpt.isPresent()) {
            throw new RuntimeException("Category id = " + id + ", not found!");
        }
        Category category = categoryOpt.get();
        if (!category.getActive()) {
            throw new RuntimeException("Category id = " + id + ", is inactive!");
        }
        return category;
    }
}
