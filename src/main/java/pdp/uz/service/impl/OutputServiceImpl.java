package pdp.uz.service.impl;

import org.springframework.stereotype.Service;
import pdp.uz.entity.*;
import pdp.uz.helpers.MapstructMapper;
import pdp.uz.helpers.Utils;
import pdp.uz.model.OutputAddDto;
import pdp.uz.model.OutputDto;
import pdp.uz.repository.OutputRepo;
import pdp.uz.service.ClientService;
import pdp.uz.service.CurrencyService;
import pdp.uz.service.OutputService;
import pdp.uz.service.WarehouseService;

import java.time.LocalDateTime;

@Service
public class OutputServiceImpl implements OutputService {
    private final OutputRepo outputRepo;
    private final ClientService clientService;
    private final WarehouseService warehouseService;
    private final CurrencyService currencyService;
    private final MapstructMapper mapper;


    public OutputServiceImpl(OutputRepo outputRepo, ClientService clientService, WarehouseService warehouseService, CurrencyService currencyService, MapstructMapper mapper) {
        this.outputRepo = outputRepo;
        this.clientService = clientService;
        this.warehouseService = warehouseService;
        this.currencyService = currencyService;
        this.mapper = mapper;
    }

    @Override
    public OutputDto add(OutputAddDto dto) {
        Long currencyId = dto.getCurrencyId();
        if (Utils.isEmpty(currencyId)) {
            throw new RuntimeException("Currency id should not be null!");
        }
        String featureNumber = dto.getFeatureNumber();
        if (Utils.isEmpty(featureNumber)) {
            throw new RuntimeException("Feature number should not be null!");
        }
        Long clientId = dto.getClientId();
        if (Utils.isEmpty(clientId)) {
            throw new RuntimeException("Client id should not be null!");
        }
        Long warehouseId = dto.getWarehouseId();
        if (Utils.isEmpty(warehouseId)) {
            throw new RuntimeException("Warehouse id should not be null!");
        }
        Client client = clientService.validate(clientId);
        Currency currency = currencyService.validate(currencyId);
        Warehouse warehouse = warehouseService.validate(warehouseId);

        Output output = new Output();
        output.setCode(Utils.generateCode());
        output.setCurrency(currency);
        output.setDate(LocalDateTime.now());
        output.setFeatureNumber(featureNumber);
        output.setClient(client);
        output.setWarehouse(warehouse);
        Output savedOutput = outputRepo.save(output);

        return mapper.toOutputDto(savedOutput);
    }
}
