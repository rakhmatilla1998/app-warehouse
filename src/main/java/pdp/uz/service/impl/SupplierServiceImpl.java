package pdp.uz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdp.uz.entity.Supplier;
import pdp.uz.helpers.MapstructMapper;
import pdp.uz.helpers.Utils;
import pdp.uz.model.SupplierAddDto;
import pdp.uz.model.SupplierDto;
import pdp.uz.repository.SupplierRepo;
import pdp.uz.service.SupplierService;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepo supplierRepo;
    private final MapstructMapper mapper;

    @Autowired
    public SupplierServiceImpl(SupplierRepo supplierRepo, MapstructMapper mapper) {
        this.supplierRepo = supplierRepo;
        this.mapper = mapper;
    }

    @Override
    public List<SupplierDto> get() {
        List<Supplier> suppliers = supplierRepo.findAll();
        return mapper.toSupplierDto(suppliers);
    }

    @Override
    public SupplierDto get(Long id) {
        Supplier supplier = validate(id);
        return mapper.toSupplierDto(supplier);
    }

    @Override
    public SupplierDto add(SupplierAddDto dto) {
        String phoneNumber = dto.getPhoneNumber();
        if (Utils.isEmpty(phoneNumber)) {
            throw new RuntimeException("Phone number should not be null");
        }
        String name = dto.getName();
        if (Utils.isEmpty(name)) {
            throw new RuntimeException("Name should not be null");
        }
        Optional<Supplier> optionalSupplier = supplierRepo.findByPhoneNumber(phoneNumber);
        if (optionalSupplier.isPresent()) {
            throw new RuntimeException("Supplier with this phone number has already existed");
        }
        Supplier supplier = mapper.toSupplier(dto);
        Supplier savedSupplier = supplierRepo.save(supplier);
        return mapper.toSupplierDto(savedSupplier);
    }

    @Override
    public SupplierDto edit(Long id, String phoneNumber, String name) {
        if (Utils.isEmpty(phoneNumber)) {
            throw new RuntimeException("Phone number should not be null");
        }
        if (Utils.isEmpty(name)) {
            throw new RuntimeException("Name should not be null");
        }

        Supplier supplier = validate(id);

        if (supplierRepo.existsByPhoneNumberAndIdNot(phoneNumber, id)) {
            throw new RuntimeException("Supplier with this phone number has already existed");
        }

        supplier.setPhoneNumber(phoneNumber);
        supplier.setName(name);
        Supplier savedSupplier = supplierRepo.save(supplier);
        return mapper.toSupplierDto(savedSupplier);
    }

    public Supplier validate(Long id) {
        Optional<Supplier> optionalSupplier = supplierRepo.findById(id);
        if (!optionalSupplier.isPresent()) {
            throw new RuntimeException("Supplier id = " + id + ", not found!");
        }
        Supplier supplier = optionalSupplier.get();
        if (!supplier.getActive()) {
            throw new RuntimeException("Supplier id = " + id + ", is inactive!");
        }
        return supplier;
    }
}
