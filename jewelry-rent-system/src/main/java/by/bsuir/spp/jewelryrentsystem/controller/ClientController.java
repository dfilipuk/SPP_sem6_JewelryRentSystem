package by.bsuir.spp.jewelryrentsystem.controller;

import by.bsuir.spp.jewelryrentsystem.dto.CreateActionResponseDto;
import by.bsuir.spp.jewelryrentsystem.dto.DeleteActionRequestDto;
import by.bsuir.spp.jewelryrentsystem.model.Client;
import by.bsuir.spp.jewelryrentsystem.dto.ClientDto;
import by.bsuir.spp.jewelryrentsystem.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/client", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_MANAGER')")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = "/list")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping(value = "/page-list")
    public List<Client> getAllClientsPageable(@RequestParam(value = "page") int pageNumber,
            @RequestParam(value = "page-size") int pageSize) {
        return clientService.getAllClientsPageable(pageNumber, pageSize);
    }

    @GetMapping(value = "/list-pages-amo")
    public long getPagesAmount(@RequestParam(value = "page-size") long pageSize) {
        return clientService.getClientsListPagesAmount(pageSize);
    }

    @GetMapping(value = "/get")
    public Client getClient(@RequestParam(value = "id") long id) {
        return clientService.getClientById(id);
    }

    @PostMapping(value = "/delete")
    public void deleteClient(@Validated @RequestBody DeleteActionRequestDto deleteActionRequestDto) {
        clientService.deleteClientById(deleteActionRequestDto.getId());
    }

    @PostMapping(value = "/create")
    public CreateActionResponseDto createClient(@Validated(ClientDto.Create.class) @RequestBody ClientDto client) {
        return clientService.createClient(client);
    }

    @PostMapping(value = "/update")
    public void updateClient(@Validated(ClientDto.Update.class) @RequestBody ClientDto client) {
        clientService.updateClient(client);
    }
}
