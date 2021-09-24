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
import pdp.uz.repository.CurrencyRepo;
import pdp.uz.repository.InputRepo;
import pdp.uz.repository.SupplierRepo;
import pdp.uz.repository.WarehouseRepo;
import pdp.uz.service.CurrencyService;
import pdp.uz.service.InputService;
import pdp.uz.service.SupplierService;
import pdp.uz.service.WarehouseService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InputServiceImpl implements InputService {
    private final InputRepo inputRepo;
    private final MapstructMapper mapper;
    private final WarehouseService warehouseService;
    private final SupplierService supplierService;
    private final CurrencyService currencyService;
    private final WarehouseRepo warehouseRepo;
    private final CurrencyRepo currencyRepo;
    private final SupplierRepo supplierRepo;


    public InputServiceImpl(InputRepo inputRepo, MapstructMapper mapper, WarehouseService warehouseService, SupplierService supplierService, CurrencyService currencyService, WarehouseRepo warehouseRepo, MapstructMapper mapstructMapper, CurrencyRepo currencyRepo, SupplierRepo supplierRepo) {
        this.inputRepo = inputRepo;
        this.mapper = mapper;
        this.warehouseService = warehouseService;
        this.supplierService = supplierService;
        this.currencyService = currencyService;
        this.warehouseRepo = warehouseRepo;
        this.currencyRepo = currencyRepo;
        this.supplierRepo = supplierRepo;
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

    @Override
    public List<InputDto> getInputs() {
        List<Input> all = inputRepo.findAll();
        return mapper.toInputDto(all);
    }

    @Override
    public InputDto getInput(Long id) {
        Optional<Input> optionalInput = inputRepo.findById(id);
        if (optionalInput.isPresent()) {
            return mapper.toInputDto(optionalInput.get());
        }
        return new InputDto();
    }

    @Override
    public InputDto deleteInput(Long id) {
        Optional<Input> optionalInput = inputRepo.findById(id);
        if (optionalInput.isPresent()) {
            InputDto inputDto = mapper.toInputDto(optionalInput.get());
            inputRepo.delete(optionalInput.get());

            return inputDto;
        }
        return new InputDto();
    }

    @Override
    public InputDto edit(Long id, InputAddDto dto) {
        Input input = validate(id);
        Warehouse validatedWarehouse = warehouseService.validate(dto.getWarehouseId());
        Supplier validatedSupplier = supplierService.validate(dto.getSupplierId());
        Currency validatedCurr = currencyService.validate(dto.getCurrencyId());

        input.setFeatureNumber(dto.getFeatureNumber());
        input.setCurrency(validatedCurr);
        input.setSupplier(validatedSupplier);
        input.setWarehouse(validatedWarehouse);

        Input savedInput = inputRepo.save(input);
        return mapper.toInputDto(savedInput);
    }

    public Input validate(Long id) {
        Optional<Input> optionalInput = inputRepo.findById(id);
        if (!optionalInput.isPresent()) {
            throw new RuntimeException("Currency id = " + id + ", not found!");
        }
        return optionalInput.get();
    }

}
