package pdp.uz.service;

import pdp.uz.entity.Category;
import pdp.uz.model.CategoryAddDto;
import pdp.uz.model.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getParents();

    List<CategoryDto> getChildren(Long id);

    CategoryDto add(CategoryAddDto dto);

    Category validate(Long id);
}
