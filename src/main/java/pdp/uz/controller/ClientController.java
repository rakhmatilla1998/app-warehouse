package pdp.uz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pdp.uz.model.*;
import pdp.uz.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/add")
    public ClientDto add(@RequestBody ClientAddDto dto) {
        return clientService.add(dto);
    }

    @GetMapping(value = "/clients")
    private List<ClientDto> getClients() {
        return clientService.getClients();
    }

    @GetMapping(value = "/client/{id}")
    private ClientDto getClient(@PathVariable Long id) {
        return clientService.getClient(id);
    }

    @DeleteMapping(value = "/delete/{id}")
    private ClientDto delete(@PathVariable Long id) {
        return clientService.delete(id);
    }

    @PostMapping(value = "/edit/{id}")
    private ClientDto edit(@PathVariable Long id, @RequestBody ClientAddDto dto) {
        return clientService.edit(id, dto);
    }

}
