package pdp.uz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pdp.uz.model.CategoryAddDto;
import pdp.uz.model.CategoryDto;
import pdp.uz.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/parents")
    private List<CategoryDto> getParents() {
        return categoryService.getParents();
    }

    @GetMapping("/{id}/children")
    private List<CategoryDto> getChildren(@PathVariable(value = "id") Long id) {
        return categoryService.getChildren(id);
    }

    @PostMapping("/add")
    private CategoryDto add(@RequestBody CategoryAddDto dto) {
        return categoryService.add(dto);
    }
}
