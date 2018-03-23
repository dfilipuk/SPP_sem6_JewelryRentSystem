package by.bsuir.spp.jewelryrentsystem.service.impl;

import by.bsuir.spp.jewelryrentsystem.dto.CreateActionResponseDto;
import by.bsuir.spp.jewelryrentsystem.dto.MaterialDto;
import by.bsuir.spp.jewelryrentsystem.model.Material;
import by.bsuir.spp.jewelryrentsystem.repository.MaterialRepository;
import by.bsuir.spp.jewelryrentsystem.service.MaterialService;
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
import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {
    private final String SORT_COLUMN = "name";

    private final MaterialRepository materialRepository;
    private final PaginationService paginationService;

    @Autowired
    public MaterialServiceImpl(MaterialRepository materialRepository, PaginationService paginationService) {
        this.materialRepository = materialRepository;
        this.paginationService = paginationService;
    }

    @Override
    public List<MaterialDto> getAllMaterials() {
        List<Material> materials;

        try {
            Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, SORT_COLUMN));
            materials = materialRepository.findAll(sort);
        } catch (Exception e) {
            throw new UnprocessableEntityException(e.getMessage());
        }

        return convertToDtoList(materials);
    }

    @Override
    public List<MaterialDto> getAllMaterialsPageable(int page, int size) {
        List<Material> materials;

        try {
            Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, SORT_COLUMN));
            Pageable pageable = new PageRequest(page, size, sort);
            materials = materialRepository.findAll(pageable).getContent();
        } catch (Exception e) {
            throw new UnprocessableEntityException(e.getMessage());
        }

        return convertToDtoList(materials);
    }

    @Override
    public long getMaterialsListPagesAmount(long pageSize) {
        return paginationService.getPagesAmount(pageSize, materialRepository.count());
    }

    @Override
    public MaterialDto getMaterialById(long id) {
        Material material = materialRepository.findOne(id);

        if (material == null) {
            throw new NotFoundException("Material not found");
        }

        return new MaterialDto(
                material.getId(),
                material.getName(),
                material.getDescription(),
                material.getParentMaterial() == null ? 0 : material.getParentMaterial().getId()
        );
    }

    @Override
    public void deleteMaterialById(long id) {
        Material material = materialRepository.findOne(id);

        if (material == null) {
            throw new UnprocessableEntityException("Material for delete not found");
        }

        if (!material.getJewelries().isEmpty()) {
            throw new UnprocessableEntityException("Unable to delete material with associated jewelries");
        }

        if (!material.getChildMaterials().isEmpty()) {
            throw new UnprocessableEntityException("Unable to delete material with associated child materials");
        }

        try {
            materialRepository.delete(material);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public CreateActionResponseDto createMaterial(MaterialDto materialDto) {
        Material material = new Material();
        saveMaterialData(material, materialDto);
        return new CreateActionResponseDto(material.getId());
    }

    @Override
    public void updateMaterial(MaterialDto materialDto) {
        Material material = materialRepository.findOne(materialDto.getId());

        if (material == null) {
            throw new UnprocessableEntityException("Material for update not found");
        }

        if (materialDto.getId() == materialDto.getParentMaterialId()) {
            throw new UnprocessableEntityException("Material can't have self as a parent");
        }

        saveMaterialData(material, materialDto);
    }

    private void saveMaterialData(Material material, MaterialDto materialDto) {
        if (materialDto.getParentMaterialId() != 0) {
            Material parentMaterial = materialRepository.findOne(materialDto.getParentMaterialId());
            if (parentMaterial == null) {
                throw new UnprocessableEntityException("Invalid parent material id");
            }

            material.setParentMaterial(parentMaterial);
        }

        material.setName(materialDto.getName());
        material.setDescription(materialDto.getDescription());

        try {
            materialRepository.saveAndFlush(material);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    private List<MaterialDto> convertToDtoList(List<Material> materials) {
        List<MaterialDto> materialsDto = new ArrayList<>();

        for (Material material : materials) {
            materialsDto.add(new MaterialDto(
                    material.getId(),
                    material.getName(),
                    material.getDescription(),
                    0
            ));
        }

        return materialsDto;
    }
}
