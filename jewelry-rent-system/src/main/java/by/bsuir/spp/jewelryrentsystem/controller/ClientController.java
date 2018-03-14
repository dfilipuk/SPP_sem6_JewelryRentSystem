package by.bsuir.spp.jewelryrentsystem.controller;

import by.bsuir.spp.jewelryrentsystem.model.Client;
import by.bsuir.spp.jewelryrentsystem.model.dto.ClientDto;
import by.bsuir.spp.jewelryrentsystem.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/client", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping(value = "/list")
    public List<Client> getAllClients(@RequestParam(value = "page") int pageNumber,
            @RequestParam(value = "page-size") int pageSize) {
        return clientService.getAllClients(pageNumber, pageSize);
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
    public void deleteClient(@RequestParam(value = "id") long id) {
        clientService.deleteClientById(id);
    }

    @PostMapping(value = "/create")
    public void createClient(@Validated(ClientDto.Create.class) @ModelAttribute ClientDto client) {
        clientService.createClient(client);
    }

    @PostMapping(value = "/update")
    public void updateClient(@Validated(ClientDto.Update.class) @ModelAttribute ClientDto client) {
        clientService.updateClient(client);
    }
}
