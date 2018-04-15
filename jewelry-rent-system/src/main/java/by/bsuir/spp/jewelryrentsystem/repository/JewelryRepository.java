package by.bsuir.spp.jewelryrentsystem.repository;

import by.bsuir.spp.jewelryrentsystem.model.Jewelry;
import by.bsuir.spp.jewelryrentsystem.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JewelryRepository extends JpaRepository<Jewelry, Long> {
    List<Jewelry> findByMaterials(Material material);
}
