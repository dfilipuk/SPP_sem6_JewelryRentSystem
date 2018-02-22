package by.bsuir.spp.jewelryrentsystem.repository;

import by.bsuir.spp.jewelryrentsystem.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
