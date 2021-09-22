package pdp.uz.service;

import pdp.uz.entity.Client;
import pdp.uz.model.ClientAddDto;
import pdp.uz.model.ClientDto;

import java.util.List;

public interface ClientService {
    ClientDto add(ClientAddDto dto);

    Client validate(Long id);

    List<ClientDto> getClients();

    ClientDto getClient(Long id);

    ClientDto delete(Long id);

    ClientDto edit(Long id, ClientAddDto dto);
}
