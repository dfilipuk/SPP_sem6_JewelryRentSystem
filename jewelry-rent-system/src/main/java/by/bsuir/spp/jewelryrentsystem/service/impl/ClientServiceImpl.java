package by.bsuir.spp.jewelryrentsystem.service.impl;

import by.bsuir.spp.jewelryrentsystem.model.Client;
import by.bsuir.spp.jewelryrentsystem.dto.ClientDto;
import by.bsuir.spp.jewelryrentsystem.repository.ClientRepository;
import by.bsuir.spp.jewelryrentsystem.service.ClientService;
import by.bsuir.spp.jewelryrentsystem.service.PaginationService;
import by.bsuir.spp.jewelryrentsystem.service.exception.InternalServerErrorException;
import by.bsuir.spp.jewelryrentsystem.service.exception.NotFoundException;
import by.bsuir.spp.jewelryrentsystem.service.exception.UnprocessableEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    private final String SORT_COLUMN = "surname";

    private final ClientRepository clientRepository;
    private final PaginationService paginationService;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, PaginationService paginationService) {
        this.clientRepository = clientRepository;
        this.paginationService = paginationService;
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> result;

        try {
            Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, SORT_COLUMN));
            result = clientRepository.findAll(sort);
        } catch (Exception e) {
            throw new UnprocessableEntityException(e.getMessage());
        }

        return result;
    }

    @Override
    public List<Client> getAllClientsPageable(int page, int size) {
        List<Client> result;

        try {
            Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, SORT_COLUMN));
            Pageable pageable = new PageRequest(page, size, sort);
            result = clientRepository.findAll(pageable).getContent();
        } catch (Exception e) {
            throw new UnprocessableEntityException(e.getMessage());
        }

        return result;
    }

    @Override
    public long getClientsListPagesAmount(long pageSize) {
        return paginationService.getPagesAmount(pageSize, clientRepository.count());
    }

    @Override
    public Client getClientById(long id) {
        Client client = clientRepository.findOne(id);

        if (client == null) {
            throw new NotFoundException("Client not found");
        }

        return client;
    }

    @Override
    public void deleteClientById(long id) {
        Client client = clientRepository.findOne(id);

        if (client == null) {
            throw new UnprocessableEntityException("Client for delete not found");
        }

        if (!client.getOrders().isEmpty()) {
            throw new UnprocessableEntityException("Unable to delete client with associated orders");
        }

        try {
            clientRepository.delete(client);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void createClient(ClientDto clientDto) {
        Client client = new Client();
        saveClientData(client, clientDto);
    }

    @Override
    public void updateClient(ClientDto clientDto) {
        Client client = clientRepository.findOne(clientDto.getId());

        if (client == null) {
            throw new UnprocessableEntityException("Client for update not found");
        }

        saveClientData(client, clientDto);
    }

    private void saveClientData(Client client, ClientDto clientDto) {
        client.setName(clientDto.getName());
        client.setSurname(clientDto.getSurname());
        client.setSecondName(clientDto.getSecondName());
        client.setAddress(clientDto.getAddress());
        client.setTelephone(clientDto.getTelephone());
        client.setPassportNumber(clientDto.getPassportNumber());

        try {
            clientRepository.saveAndFlush(client);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
