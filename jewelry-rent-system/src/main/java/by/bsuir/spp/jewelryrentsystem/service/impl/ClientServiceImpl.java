package by.bsuir.spp.jewelryrentsystem.service.impl;

import by.bsuir.spp.jewelryrentsystem.model.Client;
import by.bsuir.spp.jewelryrentsystem.repository.ClientRepository;
import by.bsuir.spp.jewelryrentsystem.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
}
