package by.bsuir.spp.jewelryrentsystem.service.impl;

import by.bsuir.spp.jewelryrentsystem.dto.JewelryDto;
import by.bsuir.spp.jewelryrentsystem.model.Branch;
import by.bsuir.spp.jewelryrentsystem.model.Jewelry;
import by.bsuir.spp.jewelryrentsystem.model.Material;
import by.bsuir.spp.jewelryrentsystem.repository.BranchRepository;
import by.bsuir.spp.jewelryrentsystem.repository.JewelryRepository;
import by.bsuir.spp.jewelryrentsystem.repository.MaterialRepository;
import by.bsuir.spp.jewelryrentsystem.service.JewelryService;
import by.bsuir.spp.jewelryrentsystem.service.PaginationService;
import by.bsuir.spp.jewelryrentsystem.service.exception.InternalServerErrorException;
import by.bsuir.spp.jewelryrentsystem.service.exception.NotFoundException;
import by.bsuir.spp.jewelryrentsystem.service.exception.UnprocessableEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class JewelryServiceImpl implements JewelryService {
    private final String SORT_COLUMN = "name";

    @Autowired
    private JewelryRepository jewelryRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private PaginationService paginationService;

    @Override
    public List<JewelryDto> getAllJewelries() {
        List<Jewelry> jewelries;

        try {
            Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, SORT_COLUMN));
            jewelries = jewelryRepository.findAll(sort);
        } catch (Exception e) {
            throw new UnprocessableEntityException(e.getMessage());
        }

        return convertToDtoList(jewelries);
    }

    @Override
    public List<JewelryDto> getAllJewelriesPageable(int page, int size) {
        List<Jewelry> jewelries;

        try {
            Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, SORT_COLUMN));
            Pageable pageable = new PageRequest(page, size, sort);
            jewelries = jewelryRepository.findAll(pageable).getContent();
        } catch (Exception e) {
            throw new UnprocessableEntityException(e.getMessage());
        }

        return convertToDtoList(jewelries);
    }

    @Override
    public long getJewelriesListPagesAmount(long pageSize) {
        return paginationService.getPagesAmount(pageSize, jewelryRepository.count());
    }

    @Override
    public JewelryDto getJewelryById(long id) {
        Jewelry jewelry = jewelryRepository.findOne(id);

        if (jewelry == null) {
            throw new NotFoundException("Jewelry not found");
        }

        return new JewelryDto(
                jewelry.getId(),
                jewelry.getName(),
                jewelry.getProducer(),
                jewelry.getDescription(),
                jewelry.getPictureUrl(),
                jewelry.getType(),
                jewelry.getWeight(),
                jewelry.getStatus(),
                jewelry.getCostPerDay(),
                jewelry.getDaysRental(),
                0,
                new ArrayList<>()
        );
    }

    @Override
    public void deleteJewelryById(long id) {
        Jewelry jewelry = jewelryRepository.findOne(id);

        if (jewelry == null) {
            throw new UnprocessableEntityException("Jewelry for delete not found");
        }

        if (!jewelry.getOrders().isEmpty()) {
            throw new UnprocessableEntityException("Unable to delete jewelry with associated orders");
        }

        if (!jewelry.getMaterials().isEmpty()) {
            jewelry.getMaterials().clear();
            jewelryRepository.saveAndFlush(jewelry);
        }

        try {
            jewelryRepository.delete(jewelry);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void createJewelry(JewelryDto jewelryDto) {
        Jewelry jewelry = new Jewelry();
        saveJewelryData(jewelry, jewelryDto);
    }

    @Override
    public void updateJewelry(JewelryDto jewelryDto) {
        Jewelry jewelry = jewelryRepository.findOne(jewelryDto.getId());

        if (jewelry == null) {
            throw new UnprocessableEntityException("Jewelry for update not found");
        }

        saveJewelryData(jewelry, jewelryDto);
    }

    private void saveJewelryData(Jewelry jewelry, JewelryDto jewelryDto) {
        Branch branch = branchRepository.findOne(jewelryDto.getBranchId());
        List<Long> materialsIds = jewelryDto.getMaterialsIds();
        Set<Material> materials = new HashSet<>();

        if (branch == null) {
            throw new UnprocessableEntityException("Invalid branch id");
        }

        if (materialsIds == null || materialsIds.size() < 1) {
            throw new UnprocessableEntityException("Jewelry must have at least one material");
        }

        for (Long materialId : materialsIds) {
            if (materialId != null) {
                Material m = materialRepository.findOne(materialId);

                if (m == null) {
                    throw new UnprocessableEntityException("Invalid material id");
                }

                materials.add(m);
            }
        }

        jewelry.setBranch(branch);
        jewelry.setMaterials(materials);
        jewelry.setName(jewelryDto.getName());
        jewelry.setProducer(jewelryDto.getProducer());
        jewelry.setDescription(jewelryDto.getDescription());
        jewelry.setPictureUrl(jewelryDto.getPictureUrl());
        jewelry.setType(jewelryDto.getType());
        jewelry.setWeight(jewelryDto.getWeight());
        jewelry.setStatus(jewelryDto.getStatus());
        jewelry.setCostPerDay(jewelryDto.getCostPerDay());
        jewelry.setDaysRental(jewelryDto.getDaysRental());

        try {
            jewelryRepository.saveAndFlush(jewelry);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    private List<JewelryDto> convertToDtoList(List<Jewelry> jewelries) {
        List<JewelryDto> jewelriesDto = new ArrayList<>();

        for (Jewelry jewelry : jewelries) {
            jewelriesDto.add(new JewelryDto(
                    jewelry.getId(),
                    jewelry.getName(),
                    jewelry.getProducer(),
                    jewelry.getDescription(),
                    jewelry.getPictureUrl(),
                    jewelry.getType(),
                    jewelry.getWeight(),
                    jewelry.getStatus(),
                    jewelry.getCostPerDay(),
                    jewelry.getDaysRental(),
                    0,
                    new ArrayList<>()
            ));
        }

        return jewelriesDto;
    }
}
