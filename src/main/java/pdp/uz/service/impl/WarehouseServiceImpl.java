package pdp.uz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdp.uz.entity.Warehouse;
import pdp.uz.helpers.MapstructMapper;
import pdp.uz.helpers.Utils;
import pdp.uz.model.WarehouseAddDto;
import pdp.uz.model.WarehouseDto;
import pdp.uz.repository.WarehouseRepo;
import pdp.uz.service.WarehouseService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepo warehouseRepo;
    private final MapstructMapper mapstructMapper;

    @Autowired
    public WarehouseServiceImpl(WarehouseRepo warehouseRepo, MapstructMapper mapstructMapper) {
        this.warehouseRepo = warehouseRepo;
        this.mapstructMapper = mapstructMapper;
    }

    @Override
    public List<WarehouseDto> getList() {
        List<Warehouse> list = warehouseRepo.findAll();
        return mapstructMapper.toWarehouseDto(list);
    }

    @Override
    public WarehouseDto add(WarehouseAddDto dto) {
        String name = dto.getName();
        if (Utils.isEmpty(name)) {
            throw new RuntimeException("Warehouse name should not be null!");
        } else {
            Optional<Warehouse> warehouseOpt = warehouseRepo.findByName(name);
            if (warehouseOpt.isPresent()) {
                throw new RuntimeException("This name is already exist!");
            }
        }

        Warehouse warehouse = mapstructMapper.toWarehouse(dto);

        Warehouse savedWarehouse = warehouseRepo.save(warehouse);

        WarehouseDto warehouseDto = mapstructMapper.toWarehouseDto(savedWarehouse);

        return warehouseDto;
    }

    @Override
    public boolean active(Warehouse warehouse) {
        if (Utils.isEmpty(warehouse)) {
            return false;
        }
        return warehouse.getActive();
    }

    @Override
    public WarehouseDto getWarehouse(Long id) {
        Optional<Warehouse> optionalWarehouse = warehouseRepo.findById(id);
        if (optionalWarehouse.isPresent()) {
            return mapstructMapper.toWarehouseDto(optionalWarehouse.get());
        }
        return new WarehouseDto();
    }

    @Override
    public WarehouseDto editWarehouse(Long id, WarehouseAddDto dto) {
        Optional<Warehouse> optionalWarehouse = warehouseRepo.findById(id);
        if (!optionalWarehouse.isPresent()) {
            throw new RuntimeException("Warehouse is not found!");
        } else {
            Warehouse warehouse = optionalWarehouse.get();
            warehouse.setName(dto.getName());
            warehouse.setActive(dto.isActive());

            Warehouse savedWarehouse = warehouseRepo.save(warehouse);

            return mapstructMapper.toWarehouseDto(savedWarehouse);
        }
    }

    @Override
    public WarehouseDto deleteWarehouse(Long id) {
        Optional<Warehouse> optionalWarehouse = warehouseRepo.findById(id);
        if (!optionalWarehouse.isPresent()) {
            throw new RuntimeException("Warehouse not found");
        }
        WarehouseDto warehouseDto = mapstructMapper.toWarehouseDto(optionalWarehouse.get());

        warehouseRepo.delete(optionalWarehouse.get());

        return warehouseDto;
    }
}
