package pdp.uz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pdp.uz.model.InputProductAddDto;
import pdp.uz.model.InputProductDto;
import pdp.uz.model.resp.ProductReport;
import pdp.uz.service.InputProductService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/input/product")
public class InputProductController {
    private final InputProductService inputProductService;

    @Autowired
    public InputProductController(InputProductService inputProductService) {
        this.inputProductService = inputProductService;
    }

    @PostMapping("/add")
    public InputProductDto add(@RequestBody InputProductAddDto dto) {
        return inputProductService.add(dto);
    }

    @PostMapping("/add/all")
    public List<InputProductDto> addAll(@RequestBody List<InputProductAddDto> dto) {
        return inputProductService.addAll(dto);
    }

    // Dashboarc
    @GetMapping("/get/today")
    public List<ProductReport> get() {
        return inputProductService.get();
    }

    @GetMapping("/get/date")
    public List<ProductReport> get(@RequestParam String date) {
        return inputProductService.get(date);
    }
}
