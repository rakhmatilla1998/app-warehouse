package pdp.uz.service.impl;

import org.springframework.stereotype.Service;
import pdp.uz.entity.Currency;
import pdp.uz.entity.Input;
import pdp.uz.entity.Supplier;
import pdp.uz.entity.Warehouse;
import pdp.uz.helpers.MapstructMapper;
import pdp.uz.helpers.Utils;
import pdp.uz.model.InputAddDto;
import pdp.uz.model.InputDto;
import pdp.uz.repository.InputRepo;
import pdp.uz.service.CurrencyService;
import pdp.uz.service.InputService;
import pdp.uz.service.SupplierService;
import pdp.uz.service.WarehouseService;

import java.time.LocalDateTime;

@Service
public class InputServiceImpl implements InputService {
    private final InputRepo inputRepo;
    private final MapstructMapper mapper;
    private final WarehouseService warehouseService;
    private final SupplierService supplierService;
    private final CurrencyService currencyService;

    public InputServiceImpl(InputRepo inputRepo, MapstructMapper mapper, WarehouseService warehouseService, SupplierService supplierService, CurrencyService currencyService) {
        this.inputRepo = inputRepo;
        this.mapper = mapper;
        this.warehouseService = warehouseService;
        this.supplierService = supplierService;
        this.currencyService = currencyService;
    }

    @Override
    public InputDto add(InputAddDto dto) {
        Long currencyId = dto.getCurrencyId();
        if (Utils.isEmpty(currencyId)){
            throw new RuntimeException("Currency id should not be null!");
        }
        String featureNumber = dto.getFeatureNumber();
        if (Utils.isEmpty(featureNumber)){
            throw new RuntimeException("Feature number should not be null!");
        }
        Long supplierId = dto.getSupplierId();
        if (Utils.isEmpty(supplierId)){
            throw new RuntimeException("Supplier id should not be null!");
        }
        Long warehouseId = dto.getWarehouseId();
        if (Utils.isEmpty(warehouseId)){
            throw new RuntimeException("Warehouse id should not be null!");
        }
        Supplier supplier = supplierService.validate(supplierId);
        Currency currency = currencyService.validate(currencyId);
        Warehouse warehouse = warehouseService.validate(warehouseId);

        Input input = new Input();
        input.setCode(Utils.generateCode());
        input.setCurrency(currency);
        input.setDate(LocalDateTime.now());
        input.setFeatureNumber(featureNumber);
        input.setSupplier(supplier);
        input.setWarehouse(warehouse);
        Input savedInput = inputRepo.save(input);

        return mapper.toInputDto(savedInput);
    }


}
