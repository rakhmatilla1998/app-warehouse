package pdp.uz.service;

import pdp.uz.entity.Warehouse;
import pdp.uz.model.WarehouseAddDto;
import pdp.uz.model.WarehouseDto;

import java.util.List;

public interface WarehouseService {

    List<WarehouseDto> getList();

    WarehouseDto add(WarehouseAddDto dto);

    boolean active(Warehouse warehouse);

    Warehouse validate(Long id);

}
