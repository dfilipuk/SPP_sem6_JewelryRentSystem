package by.bsuir.spp.jewelryrentsystem.service;

import by.bsuir.spp.jewelryrentsystem.dto.CreateActionResponseDto;
import by.bsuir.spp.jewelryrentsystem.dto.JewelryDto;
import by.bsuir.spp.jewelryrentsystem.model.Branch;
import by.bsuir.spp.jewelryrentsystem.model.Jewelry;
import by.bsuir.spp.jewelryrentsystem.repository.BranchRepository;
import by.bsuir.spp.jewelryrentsystem.repository.JewelryRepository;
import by.bsuir.spp.jewelryrentsystem.repository.MaterialRepository;
import by.bsuir.spp.jewelryrentsystem.service.exception.NotFoundException;
import by.bsuir.spp.jewelryrentsystem.service.exception.UnprocessableEntityException;
import by.bsuir.spp.jewelryrentsystem.service.impl.JewelryServiceImpl;
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
public class JewelryServiceImplTest {
    @TestConfiguration
    static class JewelryServiceImplTestContextConfiguration {

        @Autowired
        private JewelryRepository jewelryRepository;

        @Autowired
        private BranchRepository branchRepository;

        @Autowired
        private MaterialRepository materialRepository;

        private PaginationService paginationService = new PaginationServiceImpl();

        @Bean
        public JewelryService jewelryService() {
            return new JewelryServiceImpl(
                    jewelryRepository,
                    branchRepository,
                    materialRepository,
                    paginationService
            );
        }
    }

    @Autowired
    private JewelryService jewelryService;

    @MockBean
    private JewelryRepository jewelryRepository;

    @Before
    public void setUp() {
        Branch branch = new Branch();
        branch.setId(1);
        branch.setAddress("asd");
        branch.setTelephone("123548");
        branch.setEmployees(new HashSet<>());
        branch.setJewelries(new HashSet<>());

        Jewelry jewelry = new Jewelry();
        jewelry.setId(1);
        jewelry.setName("test");
        jewelry.setProducer("cat");
        jewelry.setDescription("big text");
        jewelry.setPictureUrl("url");
        jewelry.setType("type");
        jewelry.setWeight(100);
        jewelry.setStatus("on");
        jewelry.setCostPerDay(10);
        jewelry.setDaysRental(15);
        jewelry.setBranch(branch);
        jewelry.setOrders(new HashSet<>());
        jewelry.setMaterials(new HashSet<>());

        Jewelry jewelry1 = new Jewelry();
        jewelry1.setId(2);
        jewelry1.setName("test2");
        jewelry1.setProducer("cat2");
        jewelry1.setDescription("big text 2");
        jewelry1.setPictureUrl("url2");
        jewelry1.setType("type2");
        jewelry1.setWeight(1002);
        jewelry1.setStatus("on2");
        jewelry1.setCostPerDay(102);
        jewelry1.setDaysRental(152);
        jewelry1.setBranch(branch);
        jewelry1.setOrders(new HashSet<>());
        jewelry1.setMaterials(new HashSet<>());

        Jewelry jewelry2 = new Jewelry();
        jewelry2.setId(3);
        jewelry2.setName("test");
        jewelry2.setProducer("cat");
        jewelry2.setDescription("big text");
        jewelry2.setPictureUrl("url");
        jewelry2.setType("type");
        jewelry2.setWeight(100);
        jewelry2.setStatus("on");
        jewelry2.setCostPerDay(10);
        jewelry2.setDaysRental(15);
        jewelry2.setBranch(branch);
        jewelry2.setOrders(new HashSet<>());
        jewelry2.setMaterials(new HashSet<>());

        List<Jewelry> jewelries = new ArrayList<>();
        jewelries.add(jewelry);
        jewelries.add(jewelry1);
        jewelries.add(jewelry2);

        String SORT_COLUMN = "name";
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, SORT_COLUMN));

        Mockito.when(jewelryRepository.findAll(sort))
                .thenReturn(jewelries);

        Pageable pageable = new PageRequest(1, 1, sort);
        Mockito.when(jewelryRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(jewelries));

        Mockito.when(jewelryRepository.count())
                .thenReturn((long)jewelries.size());

        Mockito.when(jewelryRepository.findOne(jewelry.getId()))
                .thenReturn(jewelry);

    }

    @Test
    public void whenGetAll_thenListNotEmpty() {
        List<JewelryDto> found = jewelryService.getAllJewelries();

        assert (found.size() > 0);
    }

    @Test
    public void whenGetAll_thenListSizeCorrect() {
        List<JewelryDto> found = jewelryService.getAllJewelries();

        assert (found.size() == 3);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenGetAllPageable_thenUnprocessableEntityException() {
        List<JewelryDto> found = jewelryService.getAllJewelriesPageable(10, 10);

        assert (found.size() == 0);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenGetAllPageableNegativePage_thenUnprocessableEntityException() {
        List<JewelryDto> found = jewelryService.getAllJewelriesPageable(-10, 10);

        assert (found.size() == 0);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenGetAllPageableNegativeSize_thenUnprocessableEntityException() {
        List<JewelryDto> found = jewelryService.getAllJewelriesPageable(10, -10);

        assert (found.size() == 0);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenGetAllPageableNegativePageAndNegativeSize_thenUnprocessableEntityException() {
        List<JewelryDto> found = jewelryService.getAllJewelriesPageable(-10, -10);

        assert (found.size() == 0);
    }

    @Test
    public void whenGetAllPageable_thenCorrectSize() {
        List<JewelryDto> found = jewelryService.getAllJewelriesPageable(1, 1);

        assert (found.size() == 3);
    }

    @Test
    public void whenPageSize_thenPageAmountThree() {
        long pageSize = 1L;
        long amount = jewelryService.getJewelriesListPagesAmount(pageSize);

        assert (amount == 3);
    }

    @Test
    public void whenPageSize_thenPageAmountTwo() {
        long pageSize = 2L;
        long amount = jewelryService.getJewelriesListPagesAmount(pageSize);

        assert (amount == 2);
    }

    @Test
    public void whenPageSize_thenPageAmountOne() {
        long pageSize = 3L;
        long amount = jewelryService.getJewelriesListPagesAmount(pageSize);

        assert (amount == 1);
    }

    @Test
    public void whenBigPageSize_thenPageAmountOne() {
        long pageSize = 10L;
        long amount = jewelryService.getJewelriesListPagesAmount(pageSize);

        assert (amount == 1);
    }

    @Test
    public void whenPageSize_thenPageAmountZero() {
        long pageSize = -1L;
        long amount = jewelryService.getJewelriesListPagesAmount(pageSize);

        assert (amount == 0);
    }


    @Test
    public void whenValidId_thenGetJewelry() {
        long id = 1L;
        JewelryDto found = jewelryService.getJewelryById(id);

        assert (found.getId() == id);
    }

    @Test(expected = NotFoundException.class)
    public void whenNotValidId_thenNotFoundException() {
        long id = 10L;
        JewelryDto found = jewelryService.getJewelryById(id);

        assert (found == null);
    }

    @Test(expected = NotFoundException.class)
    public void whenNegativeId_thenNotFoundException() {
        long id = -1L;
        JewelryDto found = jewelryService.getJewelryById(id);

        assert (found == null);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenDeleteNotValid_thenUnprocessableEntityException() {
        long id = 10L;
        jewelryService.deleteJewelryById(id);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenDeleteNegative_thenUnprocessableEntityException() {
        long id = -10L;
        jewelryService.deleteJewelryById(id);
    }

    @Test
    public void whenDelete_thenNotFound() {
        long id = 1L;
        jewelryService.deleteJewelryById(id);
        assert (true);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenCreate_thenJewelryCreated() {
        JewelryDto jewelryDto = new JewelryDto();

        CreateActionResponseDto responseDto = jewelryService.createJewelry(jewelryDto);

        assert (responseDto.getId() == 0);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenUpdateNotValid_thenUnprocessableEntityException() {
        JewelryDto jewelryDto = new JewelryDto();
        jewelryDto.setId(100);

        jewelryService.updateJewelry(jewelryDto);
    }
}
