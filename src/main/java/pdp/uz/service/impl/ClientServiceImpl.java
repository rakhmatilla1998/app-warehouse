package pdp.uz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdp.uz.entity.Client;
import pdp.uz.entity.Supplier;
import pdp.uz.helpers.MapstructMapper;
import pdp.uz.helpers.Utils;
import pdp.uz.model.ClientAddDto;
import pdp.uz.model.ClientDto;
import pdp.uz.repository.ClientRepo;
import pdp.uz.service.ClientService;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepo clientRepo;
    private final MapstructMapper mapper;

    @Autowired
    public ClientServiceImpl(ClientRepo clientRepo, MapstructMapper mapper) {
        this.clientRepo = clientRepo;
        this.mapper = mapper;
    }

    @Override
    public ClientDto add(ClientAddDto dto) {
        String phoneNumber = dto.getPhoneNumber();
        if (Utils.isEmpty(phoneNumber)) {
            throw new RuntimeException("Phone number should not be null");
        }
        String name = dto.getName();
        if (Utils.isEmpty(name)) {
            throw new RuntimeException("Name should not be null");
        }
        Optional<Client> optionalClient = clientRepo.findByPhoneNumber(phoneNumber);
        if (optionalClient.isPresent()) {
            throw new RuntimeException("Client with this phone number has already existed");
        }
        Client client = mapper.toClient(dto);
        Client savedClient = clientRepo.save(client);
        return mapper.toClientDto(savedClient);
    }

    @Override
    public Client validate(Long id) {
        Optional<Client> optionalClient = clientRepo.findById(id);
        if (!optionalClient.isPresent()) {
            throw new RuntimeException("Client id = " + id + ", not found!");
        }
        Client client = optionalClient.get();
        if (!client.getActive()) {
            throw new RuntimeException("Client id = " + id + ", is inactive!");
        }
        return client;
    }
}
