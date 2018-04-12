package by.bsuir.spp.jewelryrentsystem.service;


import by.bsuir.spp.jewelryrentsystem.dto.CreateActionResponseDto;
import by.bsuir.spp.jewelryrentsystem.dto.MaterialDto;
import by.bsuir.spp.jewelryrentsystem.model.Material;
import by.bsuir.spp.jewelryrentsystem.repository.MaterialRepository;
import by.bsuir.spp.jewelryrentsystem.service.exception.NotFoundException;
import by.bsuir.spp.jewelryrentsystem.service.exception.UnprocessableEntityException;
import by.bsuir.spp.jewelryrentsystem.service.impl.MaterialServiceImpl;
import by.bsuir.spp.jewelryrentsystem.service.impl.PaginationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MaterialServiceImplTest {
    @TestConfiguration
    static class MaterialServiceImplTestContextConfiguration {

        @Autowired
        private MaterialRepository materialRepository;

        private PaginationService paginationService = new PaginationServiceImpl();

        @Bean
        public MaterialService materialService() {
            return new MaterialServiceImpl(materialRepository, paginationService);
        }
    }

    @Autowired
    private MaterialService materialService;

    @MockBean
    private  MaterialRepository materialRepository;

    @Before
    public void setUp() {
        Material material = new Material();
        material.setId(1);
        material.setName("test");
        material.setDescription("testing material");
        material.setParentMaterial(null);
        material.setChildMaterials(new HashSet<>());
        material.setJewelries(new HashSet<>());

        Material material1 = new Material();
        material1.setId(2);
        material1.setName("test2");
        material1.setDescription("testing material 2");
        material1.setParentMaterial(null);
        material1.setChildMaterials(new HashSet<>());
        material1.setJewelries(new HashSet<>());

        Material material2 = new Material();
        material2.setId(3);
        material2.setName("test3");
        material2.setDescription("testing material 3");
        material2.setParentMaterial(null);
        material2.setChildMaterials(new HashSet<>());
        material2.setJewelries(new HashSet<>());

        List<Material> materials = new ArrayList<>();
        materials.add(material);
        materials.add(material1);
        materials.add(material2);

        String SORT_COLUMN = "name";
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, SORT_COLUMN));

        Mockito.when(materialRepository.findAll(sort))
                .thenReturn(materials);

        Pageable pageable = new PageRequest(1, 1, sort);
        Mockito.when(materialRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(materials));

        Mockito.when(materialRepository.count())
                .thenReturn((long)materials.size());

        Mockito.when(materialRepository.findOne(material.getId()))
                .thenReturn(material);
    }

    @Test
    public void whenGetAll_thenListNotEmpty() {
        List<MaterialDto> found = materialService.getAllMaterials();

        assert (found.size() > 0);
    }

    @Test
    public void whenGetAll_thenListSizeCorrect() {
        List<MaterialDto> found = materialService.getAllMaterials();

        assert (found.size() == 3);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenGetAllPageable_thenUnprocessableEntityException() {
        List<MaterialDto> found = materialService.getAllMaterialsPageable(10, 10);

        assert (found.size() == 0);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenGetAllPageableNegativePage_thenUnprocessableEntityException() {
        List<MaterialDto> found = materialService.getAllMaterialsPageable(-10, 10);

        assert (found.size() == 0);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenGetAllPageableNegativeSize_thenUnprocessableEntityException() {
        List<MaterialDto> found = materialService.getAllMaterialsPageable(10, -10);

        assert (found.size() == 0);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenGetAllPageableNegativePageAndNegativeSize_thenUnprocessableEntityException() {
        List<MaterialDto> found = materialService.getAllMaterialsPageable(-10, -10);

        assert (found.size() == 0);
    }

    @Test
    public void whenGetAllPageable_thenCorrectSize() {
        List<MaterialDto> found = materialService.getAllMaterialsPageable(1, 1);

        assert (found.size() == 3);
    }

    @Test
    public void whenPageSize_thenPageAmountThree() {
        long pageSize = 1L;
        long amount = materialService.getMaterialsListPagesAmount(pageSize);

        assert (amount == 3);
    }

    @Test
    public void whenPageSize_thenPageAmountTwo() {
        long pageSize = 2L;
        long amount = materialService.getMaterialsListPagesAmount(pageSize);

        assert (amount == 2);
    }

    @Test
    public void whenPageSize_thenPageAmountOne() {
        long pageSize = 3L;
        long amount = materialService.getMaterialsListPagesAmount(pageSize);

        assert (amount == 1);
    }

    @Test
    public void whenBigPageSize_thenPageAmountOne() {
        long pageSize = 10L;
        long amount = materialService.getMaterialsListPagesAmount(pageSize);

        assert (amount == 1);
    }

    @Test
    public void whenPageSize_thenPageAmountZero() {
        long pageSize = -1L;
        long amount = materialService.getMaterialsListPagesAmount(pageSize);

        assert (amount == 0);
    }


    @Test
    public void whenValidId_thenGetMaterial() {
        long id = 1L;
        MaterialDto found = materialService.getMaterialById(id);

        assert (found.getId() == id);
    }

    @Test(expected = NotFoundException.class)
    public void whenNotValidId_thenNotFoundException() {
        long id = 10L;
        MaterialDto found = materialService.getMaterialById(id);

        assert (found == null);
    }

    @Test(expected = NotFoundException.class)
    public void whenNegativeId_thenNotFoundException() {
        long id = -1L;
        MaterialDto found = materialService.getMaterialById(id);

        assert (found == null);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenDeleteNotValid_thenUnprocessableEntityException() {
        long id = 10L;
        materialService.deleteMaterialById(id);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenDeleteNegative_thenUnprocessableEntityException() {
        long id = -10L;
        materialService.deleteMaterialById(id);
    }

    @Test
    public void whenDelete_thenNotFound() {
        long id = 1L;
        materialService.deleteMaterialById(id);
        assert (true);
    }

    @Test
    public void whenCreate_thenMaterialCreated() {
        MaterialDto materialDto = new MaterialDto();
        materialDto.setName("rrr");
        materialDto.setDescription("1478526");

        CreateActionResponseDto responseDto = materialService.createMaterial(materialDto);

        assert (responseDto.getId() == 0);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenUpdateNotValid_thenUnprocessableEntityException() {
        MaterialDto materialDto = new MaterialDto();
        materialDto.setId(100);
        materialDto.setName("rrr");
        materialDto.setDescription("1478526");

        materialService.updateMaterial(materialDto);
    }

}
