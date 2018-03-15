package by.bsuir.spp.jewelryrentsystem.service;

import by.bsuir.spp.jewelryrentsystem.dto.OrderDto;
import by.bsuir.spp.jewelryrentsystem.model.Order;

import java.util.List;

public interface OrderService {
    List<OrderDto> getAllOrders();
    List<Order> getAllOrdersPageable(int page, int size);
    long getOrdersListPagesAmount(long pageSize);
    OrderDto getOrderById(long id);
    void deleteOrderById(long id);
    void createOrder(OrderDto clientDto);
    void updateOrder(OrderDto clientDto);
}
