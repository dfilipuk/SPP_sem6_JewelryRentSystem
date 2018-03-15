package by.bsuir.spp.jewelryrentsystem.service;

import by.bsuir.spp.jewelryrentsystem.dto.JewelryDto;

import java.util.List;

public interface JewelryService {
    List<JewelryDto> getAllJewelries();
    List<JewelryDto> getAllJewelriesPageable(int page, int size);
    long getJewelriesListPagesAmount(long pageSize);
    JewelryDto getJewelryById(long id);
    void deleteJewelryById(long id);
    void createJewelry(JewelryDto jewelryDto);
    void updateJewelry(JewelryDto jewelryDto);
}
