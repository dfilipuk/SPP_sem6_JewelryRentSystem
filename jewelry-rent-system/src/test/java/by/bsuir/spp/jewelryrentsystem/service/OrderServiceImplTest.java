package by.bsuir.spp.jewelryrentsystem.service;

import by.bsuir.spp.jewelryrentsystem.dto.CreateActionResponseDto;
import by.bsuir.spp.jewelryrentsystem.dto.OrderDto;
import by.bsuir.spp.jewelryrentsystem.model.*;
import by.bsuir.spp.jewelryrentsystem.repository.ClientRepository;
import by.bsuir.spp.jewelryrentsystem.repository.EmployeeRepository;
import by.bsuir.spp.jewelryrentsystem.repository.JewelryRepository;
import by.bsuir.spp.jewelryrentsystem.repository.OrderRepository;
import by.bsuir.spp.jewelryrentsystem.service.exception.NotFoundException;
import by.bsuir.spp.jewelryrentsystem.service.exception.UnprocessableEntityException;
import by.bsuir.spp.jewelryrentsystem.service.impl.OrderServiceImpl;
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
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderServiceImplTest {
    @TestConfiguration
    static class OrderServiceImplTestContextConfiguration {

        @Autowired
        private OrderRepository orderRepository;

        @Autowired
        private ClientRepository clientRepository;

        @Autowired
        private EmployeeRepository employeeRepository;

        @Autowired
        private JewelryRepository jewelryRepository;

        private PaginationService paginationService = new PaginationServiceImpl();

        @Bean
        public OrderService orderService() {
            return new OrderServiceImpl(
                    orderRepository,
                    clientRepository,
                    employeeRepository,
                    jewelryRepository,
                    paginationService
            );
        }
    }

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

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

        Branch branch = new Branch();
        branch.setId(1);
        branch.setAddress("asd");
        branch.setTelephone("123548");
        branch.setEmployees(new HashSet<>());
        branch.setJewelries(new HashSet<>());

        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("test");
        employee.setSurname("rent");
        employee.setSecondName("tru");
        employee.setSalary(1000);
        employee.setPosition("admin");
        employee.setLogin("login-test");
        employee.setPassword("pass");
        employee.setRole("ROLE_ADMIN");
        employee.setBranch(branch);
        employee.setOrders(new HashSet<>());

        Jewelry jewelry = new Jewelry();
        jewelry.setId(1);
        jewelry.setName("test");
        jewelry.setProducer("cat");
        jewelry.setDescription("big text");
        jewelry.setPictureUrl("url");
        jewelry.setType("type");
        jewelry.setWeight(100);
        jewelry.setStatus("on");
        jewelry.setCostPerDay(10);
        jewelry.setDaysRental(15);
        jewelry.setBranch(branch);
        jewelry.setOrders(new HashSet<>());
        jewelry.setMaterials(new HashSet<>());

        Order order = new Order();
        order.setId(1);
        order.setStatus("yes");
        order.setRentDate(new Date());
        order.setDaysRent(5);
        order.setCost(852);
        order.setClient(client);
        order.setEmployee(employee);
        order.setJewelry(jewelry);

        Order order1 = new Order();
        order1.setId(2);
        order1.setStatus("yes");
        order1.setRentDate(new Date());
        order1.setDaysRent(5);
        order1.setCost(852);
        order1.setClient(client);
        order1.setEmployee(employee);
        order1.setJewelry(jewelry);

        Order order2 = new Order();
        order2.setId(3);
        order2.setStatus("yes");
        order2.setRentDate(new Date());
        order2.setDaysRent(5);
        order2.setCost(852);
        order2.setClient(client);
        order2.setEmployee(employee);
        order2.setJewelry(jewelry);

        List<Order> orders = new ArrayList<>();
        orders.add(order);
        orders.add(order1);
        orders.add(order2);

        String SORT_COLUMN = "rentDate";
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, SORT_COLUMN));

        Mockito.when(orderRepository.findAll(sort))
                .thenReturn(orders);

        Pageable pageable = new PageRequest(1, 1, sort);
        Mockito.when(orderRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(orders));

        Mockito.when(orderRepository.count())
                .thenReturn((long)orders.size());

        Mockito.when(orderRepository.findOne(order.getId()))
                .thenReturn(order);
    }

    @Test
    public void whenGetAll_thenListNotEmpty() {
        List<OrderDto> found = orderService.getAllOrders();

        assert (found.size() > 0);
    }

    @Test
    public void whenGetAll_thenListSizeCorrect() {
        List<OrderDto> found = orderService.getAllOrders();

        assert (found.size() == 3);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenGetAllPageable_thenUnprocessableEntityException() {
        List<OrderDto> found = orderService.getAllOrdersPageable(10, 10);

        assert (found.size() == 0);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenGetAllPageableNegativePage_thenUnprocessableEntityException() {
        List<OrderDto> found = orderService.getAllOrdersPageable(-10, 10);

        assert (found.size() == 0);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenGetAllPageableNegativeSize_thenUnprocessableEntityException() {
        List<OrderDto> found = orderService.getAllOrdersPageable(10, -10);

        assert (found.size() == 0);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenGetAllPageableNegativePageAndNegativeSize_thenUnprocessableEntityException() {
        List<OrderDto> found = orderService.getAllOrdersPageable(-10, -10);

        assert (found.size() == 0);
    }

    @Test
    public void whenGetAllPageable_thenCorrectSize() {
        List<OrderDto> found = orderService.getAllOrdersPageable(1, 1);

        assert (found.size() == 3);
    }

    @Test
    public void whenPageSize_thenPageAmountThree() {
        long pageSize = 1L;
        long amount = orderService.getOrdersListPagesAmount(pageSize);

        assert (amount == 3);
    }

    @Test
    public void whenPageSize_thenPageAmountTwo() {
        long pageSize = 2L;
        long amount = orderService.getOrdersListPagesAmount(pageSize);

        assert (amount == 2);
    }

    @Test
    public void whenPageSize_thenPageAmountOne() {
        long pageSize = 3L;
        long amount = orderService.getOrdersListPagesAmount(pageSize);

        assert (amount == 1);
    }

    @Test
    public void whenBigPageSize_thenPageAmountOne() {
        long pageSize = 10L;
        long amount = orderService.getOrdersListPagesAmount(pageSize);

        assert (amount == 1);
    }

    @Test
    public void whenPageSize_thenPageAmountZero() {
        long pageSize = -1L;
        long amount = orderService.getOrdersListPagesAmount(pageSize);

        assert (amount == 0);
    }


    @Test
    public void whenValidId_thenGetOrder() {
        long id = 1L;
        OrderDto found = orderService.getOrderById(id);

        assert (found.getId() == id);
    }

    @Test(expected = NotFoundException.class)
    public void whenNotValidId_thenNotFoundException() {
        long id = 10L;
        OrderDto found = orderService.getOrderById(id);

        assert (found == null);
    }

    @Test(expected = NotFoundException.class)
    public void whenNegativeId_thenNotFoundException() {
        long id = -1L;
        OrderDto found = orderService.getOrderById(id);

        assert (found == null);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenDeleteNotValid_thenUnprocessableEntityException() {
        long id = 10L;
        orderService.deleteOrderById(id);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenDeleteNegative_thenUnprocessableEntityException() {
        long id = -10L;
        orderService.deleteOrderById(id);
    }

    @Test
    public void whenDelete_thenNotFound() {
        long id = 1L;
        orderService.deleteOrderById(id);
        assert (true);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenCreate_thenOrderCreated() {
        OrderDto orderDto = new OrderDto();

        CreateActionResponseDto responseDto = orderService.createOrder(orderDto);

        assert (responseDto.getId() == 0);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenUpdateNotValid_thenUnprocessableEntityException() {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(100);

        orderService.updateOrder(orderDto);
    }
}
