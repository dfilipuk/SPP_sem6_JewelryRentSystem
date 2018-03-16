package by.bsuir.spp.jewelryrentsystem.repository;

import by.bsuir.spp.jewelryrentsystem.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
