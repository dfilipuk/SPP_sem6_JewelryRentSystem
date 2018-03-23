package by.bsuir.spp.jewelryrentsystem.service;

import by.bsuir.spp.jewelryrentsystem.dto.CreateActionResponseDto;
import by.bsuir.spp.jewelryrentsystem.dto.MaterialDto;

import java.util.List;

public interface MaterialService {
    List<MaterialDto> getAllMaterials();
    List<MaterialDto> getAllMaterialsPageable(int page, int size);
    long getMaterialsListPagesAmount(long pageSize);
    MaterialDto getMaterialById(long id);
    void deleteMaterialById(long id);
    CreateActionResponseDto createMaterial(MaterialDto materialDto);
    void updateMaterial(MaterialDto materialDto);
}
