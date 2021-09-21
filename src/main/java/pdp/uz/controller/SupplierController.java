package pdp.uz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pdp.uz.model.SupplierAddDto;
import pdp.uz.model.SupplierDto;
import pdp.uz.service.SupplierService;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {
    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/get")
    public List<SupplierDto> get() {
        return supplierService.get();
    }

    @GetMapping("/get/{id}")
    public SupplierDto get(@PathVariable(value = "id") Long id) {
        return supplierService.get(id);
    }

    @PostMapping("/add")
    public SupplierDto add(@RequestBody SupplierAddDto dto) {
        return supplierService.add(dto);
    }

    @PutMapping("/edit/{id}")
    public SupplierDto edit(@PathVariable(value = "id") Long id,
                            @RequestParam String phoneNumber,
                            @RequestParam String name) {
        return supplierService.edit(id, phoneNumber, name);
    }
}
