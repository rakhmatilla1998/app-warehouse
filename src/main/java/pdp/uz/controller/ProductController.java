package pdp.uz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pdp.uz.model.ProductAddDto;
import pdp.uz.model.ProductDto;
import pdp.uz.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/get/{id}")
    private ProductDto get(@PathVariable(value = "id") Long id) {
        return productService.get(id);
    }

    @GetMapping("/get/all")
    private List<ProductDto> getAll() {
        return productService.getAll();
    }

    @GetMapping("/get/category/{id}/products")
    private List<ProductDto> getAllByCategory(@PathVariable(value = "id") Long categoryId) {
        return productService.getAllByCategory(categoryId);
    }

    @GetMapping("/get/measurement/{id}/products")
    private List<ProductDto> getAllByMeasurement(@PathVariable(value = "id") Long measurementId) {
        return productService.getAllByMeasurement(measurementId);
    }

    @PostMapping("/add")
    private ProductDto add(@RequestBody ProductAddDto dto) {
        return productService.add(dto);
    }
}
