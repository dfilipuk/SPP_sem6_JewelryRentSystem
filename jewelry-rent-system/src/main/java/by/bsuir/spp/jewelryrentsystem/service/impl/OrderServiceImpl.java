package by.bsuir.spp.jewelryrentsystem.service.impl;

import by.bsuir.spp.jewelryrentsystem.dto.OrderDto;
import by.bsuir.spp.jewelryrentsystem.model.Client;
import by.bsuir.spp.jewelryrentsystem.model.Employee;
import by.bsuir.spp.jewelryrentsystem.model.Jewelry;
import by.bsuir.spp.jewelryrentsystem.model.Order;
import by.bsuir.spp.jewelryrentsystem.repository.ClientRepository;
import by.bsuir.spp.jewelryrentsystem.repository.EmployeeRepository;
import by.bsuir.spp.jewelryrentsystem.repository.JewelryRepository;
import by.bsuir.spp.jewelryrentsystem.repository.OrderRepository;
import by.bsuir.spp.jewelryrentsystem.service.OrderService;
import by.bsuir.spp.jewelryrentsystem.service.PaginationService;
import by.bsuir.spp.jewelryrentsystem.service.exception.InternalServerErrorException;
import by.bsuir.spp.jewelryrentsystem.service.exception.NotFoundException;
import by.bsuir.spp.jewelryrentsystem.service.exception.UnprocessableEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final String SORT_COLUMN = "rentDate";
    private final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final JewelryRepository jewelryRepository;
    private final PaginationService paginationService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            ClientRepository clientRepository,
                            EmployeeRepository employeeRepository,
                            JewelryRepository jewelryRepository,
                            PaginationService paginationService
    ) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
        this.jewelryRepository = jewelryRepository;
        this.paginationService = paginationService;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders;

        try {
            Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, SORT_COLUMN));
            orders = orderRepository.findAll(sort);
        } catch (Exception e) {
            throw new UnprocessableEntityException(e.getMessage());
        }

        return convertToDtoList(orders);
    }

    @Override
    public List<OrderDto> getAllOrdersPageable(int page, int size) {
        List<Order> orders;

        try {
            Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, SORT_COLUMN));
            Pageable pageable = new PageRequest(page, size, sort);
            orders = orderRepository.findAll(pageable).getContent();
        } catch (Exception e) {
            throw new UnprocessableEntityException(e.getMessage());
        }

        return convertToDtoList(orders);
    }

    @Override
    public long getOrdersListPagesAmount(long pageSize) {
        return paginationService.getPagesAmount(pageSize, orderRepository.count());
    }

    @Override
    public OrderDto getOrderById(long id) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        Order order = orderRepository.findOne(id);

        if (order == null) {
            throw new NotFoundException("Order not found");
        }

        return new OrderDto(
                order.getId(),
                order.getStatus(),
                formatter.format(order.getRentDate()),
                order.getDaysRent(),
                order.getCost(),
                order.getClient().getId(),
                order.getEmployee().getId(),
                order.getJewelry().getId()
        );
    }

    @Override
    public void deleteOrderById(long id) {
        Order order = orderRepository.findOne(id);

        if (order == null) {
            throw new UnprocessableEntityException("Order for delete not found");
        }

        try {
            orderRepository.delete(order);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void createOrder(OrderDto orderDto) {
        Order order = new Order();
        saveOrderData(order, orderDto);
    }

    @Override
    public void updateOrder(OrderDto orderDto) {
        Order order = orderRepository.findOne(orderDto.getId());

        if (order == null) {
            throw new UnprocessableEntityException("Order for update not found");
        }

        saveOrderData(order, orderDto);
    }

    private void saveOrderData(Order order, OrderDto orderDto) {
        Client client = clientRepository.findOne(orderDto.getClientId());
        Employee employee = employeeRepository.findOne(orderDto.getEmployeeId());
        Jewelry jewelry = jewelryRepository.findOne(orderDto.getJewelryId());
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

        if (client == null) {
            throw new UnprocessableEntityException("Invalid client id");
        }

        if (employee == null) {
            throw new UnprocessableEntityException("Invalid employee id");
        }

        if (jewelry == null) {
            throw new UnprocessableEntityException("Invalid jewelry id");
        }

        try {
            order.setRentDate(formatter.parse(orderDto.getRentDate()));
        } catch (ParseException e) {
            throw new UnprocessableEntityException(e.getMessage());
        }

        order.setStatus(orderDto.getStatus());
        order.setDaysRent(orderDto.getDaysRent());
        order.setCost(orderDto.getCost());
        order.setClient(client);
        order.setEmployee(employee);
        order.setJewelry(jewelry);

        try {
            orderRepository.saveAndFlush(order);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    private List<OrderDto> convertToDtoList(List<Order> orders) {
        List<OrderDto> ordersDto = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

        for (Order order : orders) {
            ordersDto.add(new OrderDto(
                    order.getId(),
                    order.getStatus(),
                    formatter.format(order.getRentDate()),
                    order.getDaysRent(),
                    order.getCost(),
                    0,
                    0,
                    0
            ));
        }

        return ordersDto;
    }
}
