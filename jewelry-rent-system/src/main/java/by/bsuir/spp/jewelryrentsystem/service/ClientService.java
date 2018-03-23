package by.bsuir.spp.jewelryrentsystem.service;

import by.bsuir.spp.jewelryrentsystem.dto.CreateActionResponseDto;
import by.bsuir.spp.jewelryrentsystem.model.Client;
import by.bsuir.spp.jewelryrentsystem.dto.ClientDto;

import java.util.List;

public interface ClientService {
    List<Client> getAllClients();
    List<Client> getAllClientsPageable(int page, int size);
    long getClientsListPagesAmount(long pageSize);
    void deleteClientById(long id);
    Client getClientById(long id);
    CreateActionResponseDto createClient(ClientDto clientDto);
    void updateClient(ClientDto clientDto);
}
