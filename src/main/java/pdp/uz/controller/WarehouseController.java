package pdp.uz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pdp.uz.model.WarehouseAddDto;
import pdp.uz.model.WarehouseDto;
import pdp.uz.service.WarehouseService;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping("/get/list")
    private List<WarehouseDto> getList() {
        return warehouseService.getList();
    }

    @PostMapping("/add")
    private WarehouseDto add(@RequestBody WarehouseAddDto dto) {
        return warehouseService.add(dto);
    }

    @GetMapping(value = "/get/warehouse/{id}")
    private WarehouseDto getWarehouse(@PathVariable Long id) {
        return warehouseService.getWarehouse(id);
    }

    @PutMapping(value = "/editWarehouse/{id}")
    private WarehouseDto edit(@PathVariable Long id, @RequestBody WarehouseAddDto dto) {
        return warehouseService.editWarehouse(id, dto);
    }

    @DeleteMapping(value = "/deleteWarehouse/{id}")
    private WarehouseDto delete(@PathVariable Long id) {
        return warehouseService.deleteWarehouse(id);
    }
}
