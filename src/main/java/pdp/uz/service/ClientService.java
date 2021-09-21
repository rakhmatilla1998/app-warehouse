package pdp.uz.service;

import pdp.uz.entity.Client;
import pdp.uz.model.ClientAddDto;
import pdp.uz.model.ClientDto;

public interface ClientService {
    ClientDto add(ClientAddDto dto);

    Client validate(Long id);
}
