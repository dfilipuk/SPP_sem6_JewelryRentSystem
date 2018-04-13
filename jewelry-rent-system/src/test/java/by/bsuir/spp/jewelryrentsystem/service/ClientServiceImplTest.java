package by.bsuir.spp.jewelryrentsystem.service;

import by.bsuir.spp.jewelryrentsystem.dto.ClientDto;
import by.bsuir.spp.jewelryrentsystem.dto.CreateActionResponseDto;
import by.bsuir.spp.jewelryrentsystem.model.Client;
import by.bsuir.spp.jewelryrentsystem.repository.ClientRepository;
import by.bsuir.spp.jewelryrentsystem.service.exception.NotFoundException;
import by.bsuir.spp.jewelryrentsystem.service.exception.UnprocessableEntityException;
import by.bsuir.spp.jewelryrentsystem.service.impl.ClientServiceImpl;
import by.bsuir.spp.jewelryrentsystem.service.impl.PaginationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClientServiceImplTest {
    @TestConfiguration
    static class ClientServiceImplTestContextConfiguration {

        @Autowired
        private ClientRepository clientRepository;

        private PaginationService paginationService = new PaginationServiceImpl();

        @Bean
        public ClientService clientService() {
            return new ClientServiceImpl(clientRepository, paginationService);
        }
    }

    @Autowired
    private ClientService clientService;

    @MockBean
    private ClientRepository clientRepository;

    @Before
    public void setUp() {
        Client client = new Client();
        client.setId(1);
        client.setName("test");
        client.setSurname("abram");
        client.setSecondName("dim");
        client.setPassportNumber("1478523");
        client.setAddress("address");
        client.setTelephone("9663325");
        client.setOrders(new HashSet<>());

        Client client1 = new Client();
        client1.setId(2);
        client1.setName("test2");
        client1.setSurname("abram2");
        client1.setSecondName("dim2");
        client1.setPassportNumber("1478523");
        client1.setAddress("address2");
        client1.setTelephone("9663325");
        client1.setOrders(new HashSet<>());

        Client client2 = new Client();
        client2.setId(3);
        client2.setName("test3");
        client2.setSurname("abram3");
        client2.setSecondName("dim3");
        client2.setPassportNumber("1478523");
        client2.setAddress("address3");
        client2.setTelephone("9663325");
        client2.setOrders(new HashSet<>());

        List<Client> clients = new ArrayList<>();
        clients.add(client);
        clients.add(client1);
        clients.add(client2);

        String SORT_COLUMN = "surname";
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, SORT_COLUMN));

        Mockito.when(clientRepository.findAll(sort))
                .thenReturn(clients);

        Pageable pageable = new PageRequest(1, 1, sort);
        Mockito.when(clientRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(clients));

        Mockito.when(clientRepository.count())
                .thenReturn((long)clients.size());

        Mockito.when(clientRepository.findOne(client.getId()))
                .thenReturn(client);
    }

    @Test
    public void whenGetAll_thenListNotEmpty() {
        List<Client> found = clientService.getAllClients();

        assert (found.size() > 0);
    }

    @Test
    public void whenGetAll_thenListSizeCorrect() {
        List<Client> found = clientService.getAllClients();

        assert (found.size() == 3);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenGetAllPageable_thenUnprocessableEntityException() {
        List<Client> found = clientService.getAllClientsPageable(10, 10);

        assert (found.size() == 0);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenGetAllPageableNegativePage_thenUnprocessableEntityException() {
        List<Client> found = clientService.getAllClientsPageable(-10, 10);

        assert (found.size() == 0);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenGetAllPageableNegativeSize_thenUnprocessableEntityException() {
        List<Client> found = clientService.getAllClientsPageable(10, -10);

        assert (found.size() == 0);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenGetAllPageableNegativePageAndNegativeSize_thenUnprocessableEntityException() {
        List<Client> found = clientService.getAllClientsPageable(-10, -10);

        assert (found.size() == 0);
    }

    @Test
    public void whenGetAllPageable_thenCorrectSize() {
        List<Client> found = clientService.getAllClientsPageable(1, 1);

        assert (found.size() == 3);
    }

    @Test
    public void whenPageSize_thenPageAmountThree() {
        long pageSize = 1L;
        long amount = clientService.getClientsListPagesAmount(pageSize);

        assert (amount == 3);
    }

    @Test
    public void whenPageSize_thenPageAmountTwo() {
        long pageSize = 2L;
        long amount = clientService.getClientsListPagesAmount(pageSize);

        assert (amount == 2);
    }

    @Test
    public void whenPageSize_thenPageAmountOne() {
        long pageSize = 3L;
        long amount = clientService.getClientsListPagesAmount(pageSize);

        assert (amount == 1);
    }

    @Test
    public void whenBigPageSize_thenPageAmountOne() {
        long pageSize = 10L;
        long amount = clientService.getClientsListPagesAmount(pageSize);

        assert (amount == 1);
    }

    @Test
    public void whenPageSize_thenPageAmountZero() {
        long pageSize = -1L;
        long amount = clientService.getClientsListPagesAmount(pageSize);

        assert (amount == 0);
    }


    @Test
    public void whenValidId_thenGetClient() {
        long id = 1L;
        Client found = clientService.getClientById(id);

        assert (found.getId() == id);
    }

    @Test(expected = NotFoundException.class)
    public void whenNotValidId_thenNotFoundException() {
        long id = 10L;
        Client found = clientService.getClientById(id);

        assert (found == null);
    }

    @Test(expected = NotFoundException.class)
    public void whenNegativeId_thenNotFoundException() {
        long id = -1L;
        Client found = clientService.getClientById(id);

        assert (found == null);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenDeleteNotValid_thenUnprocessableEntityException() {
        long id = 10L;
        clientService.deleteClientById(id);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenDeleteNegative_thenUnprocessableEntityException() {
        long id = -10L;
        clientService.deleteClientById(id);
    }

    @Test
    public void whenDelete_thenNotFound() {
        long id = 1L;
        clientService.deleteClientById(id);
        assert (true);
    }

    @Test
    public void whenCreate_thenClientCreated() {
        ClientDto clientDto = new ClientDto();
        clientDto.setAddress("rrr");
        clientDto.setTelephone("1478526");

        CreateActionResponseDto responseDto = clientService.createClient(clientDto);

        assert (responseDto.getId() == 0);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenUpdateNotValid_thenUnprocessableEntityException() {
        ClientDto clientDto = new ClientDto();
        clientDto.setId(100);
        clientDto.setAddress("rrr");
        clientDto.setTelephone("1478526");

        clientService.updateClient(clientDto);
    }
}
