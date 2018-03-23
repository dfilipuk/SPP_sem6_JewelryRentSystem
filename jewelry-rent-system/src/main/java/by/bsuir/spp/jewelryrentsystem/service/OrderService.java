package by.bsuir.spp.jewelryrentsystem.service;

import by.bsuir.spp.jewelryrentsystem.dto.CreateActionResponseDto;
import by.bsuir.spp.jewelryrentsystem.dto.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getAllOrders();
    List<OrderDto> getAllOrdersPageable(int page, int size);
    long getOrdersListPagesAmount(long pageSize);
    OrderDto getOrderById(long id);
    void deleteOrderById(long id);
    CreateActionResponseDto createOrder(OrderDto orderDto);
    void updateOrder(OrderDto orderDto);
}
