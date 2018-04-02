package by.bsuir.spp.jewelryrentsystem.controller;

import by.bsuir.spp.jewelryrentsystem.dto.CreateActionResponseDto;
import by.bsuir.spp.jewelryrentsystem.dto.DeleteActionRequestDto;
import by.bsuir.spp.jewelryrentsystem.dto.OrderDto;
import by.bsuir.spp.jewelryrentsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_MANAGER')")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/list")
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping(value = "/page-list")
    public List<OrderDto> getAllOrdersPageable(@RequestParam(value = "page") int pageNumber,
                                             @RequestParam(value = "page-size") int pageSize) {
        return orderService.getAllOrdersPageable(pageNumber, pageSize);
    }

    @GetMapping(value = "/list-pages-amo")
    public long getPagesAmount(@RequestParam(value = "page-size") long pageSize) {
        return orderService.getOrdersListPagesAmount(pageSize);
    }

    @GetMapping(value = "/get")
    public OrderDto getOrder(@RequestParam(value = "id") long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping(value = "/delete")
    public void deleteOrder(@Validated @RequestBody DeleteActionRequestDto deleteActionRequestDto) {
        orderService.deleteOrderById(deleteActionRequestDto.getId());
    }

    @PostMapping(value = "/create")
    public CreateActionResponseDto createOrder(@Validated(OrderDto.Create.class) @RequestBody OrderDto order) {
        return orderService.createOrder(order);
    }

    @PostMapping(value = "/update")
    public void updateOrder(@Validated(OrderDto.Update.class) @RequestBody OrderDto order) {
        orderService.updateOrder(order);
    }
}
