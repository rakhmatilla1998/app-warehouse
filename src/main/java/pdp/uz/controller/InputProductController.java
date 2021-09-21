package pdp.uz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pdp.uz.model.InputProductAddDto;
import pdp.uz.model.InputProductDto;
import pdp.uz.service.InputProductService;

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
    public InputProductDto add(@RequestBody InputProductAddDto dto){
        return inputProductService.add(dto);
    }

    @PostMapping("/add/all")
    public List<InputProductDto> addAll(@RequestBody List<InputProductAddDto> dto){
        return inputProductService.addAll(dto);
    }
}
