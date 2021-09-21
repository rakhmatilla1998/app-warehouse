package pdp.uz.service;

import pdp.uz.entity.Supplier;
import pdp.uz.model.SupplierAddDto;
import pdp.uz.model.SupplierDto;

import java.util.List;

public interface SupplierService {
    List<SupplierDto> get();

    SupplierDto get(Long id);

    SupplierDto add(SupplierAddDto dto);

    SupplierDto edit(Long id, String phoneNumber, String name);

    Supplier validate(Long id);
}
