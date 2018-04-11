package by.bsuir.spp.jewelryrentsystem.service;

import by.bsuir.spp.jewelryrentsystem.dto.BranchDto;
import by.bsuir.spp.jewelryrentsystem.dto.CreateActionResponseDto;
import by.bsuir.spp.jewelryrentsystem.model.Branch;
import by.bsuir.spp.jewelryrentsystem.repository.BranchRepository;
import by.bsuir.spp.jewelryrentsystem.service.exception.NotFoundException;
import by.bsuir.spp.jewelryrentsystem.service.exception.UnprocessableEntityException;
import by.bsuir.spp.jewelryrentsystem.service.impl.BranchServiceImpl;
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
public class BranchServiceImplTest {
    @TestConfiguration
    static class BranchServiceImplTestContextConfiguration {

        @Autowired
        private BranchRepository branchRepository;

        private PaginationService paginationService = new PaginationServiceImpl();

        @Bean
        public BranchService branchService() {
            return new BranchServiceImpl(branchRepository, paginationService);
        }
    }

    @Autowired
    private BranchService branchService;

    @MockBean
    private BranchRepository branchRepository;

    @Before
    public void setUp() {
        Branch branch = new Branch();
        branch.setId(1);
        branch.setAddress("asd");
        branch.setTelephone("123548");
        branch.setEmployees(new HashSet<>());
        branch.setJewelries(new HashSet<>());

        Branch branch1 = new Branch();
        branch1.setId(2);
        branch1.setAddress("asd2");
        branch1.setTelephone("1235482");

        Branch branch2 = new Branch();
        branch2.setId(3);
        branch2.setAddress("asd3");
        branch2.setTelephone("1235483");

        List<Branch> branches = new ArrayList<>();
        branches.add(branch);
        branches.add(branch1);
        branches.add(branch2);

        String SORT_COLUMN = "address";
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, SORT_COLUMN));
        Mockito.when(branchRepository.findAll(sort))
                .thenReturn(branches);

        Pageable pageable = new PageRequest(1, 1, sort);
        Mockito.when(branchRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(branches));

        Mockito.when(branchRepository.count())
                .thenReturn((long)branches.size());

        Mockito.when(branchRepository.findOne(branch.getId()))
                .thenReturn(branch);
    }

    @Test
    public void whenGetAll_thenListNotEmpty() {
        List<Branch> found = branchService.getAllBranches();

        assert (found.size() > 0);
    }

    @Test
    public void whenGetAll_thenListSizeCorrect() {
        List<Branch> found = branchService.getAllBranches();

        assert (found.size() == 3);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenGetAllPageable_thenUnprocessableEntityException() {
        List<Branch> found = branchService.getAllBranchesPageable(10, 10);

        assert (found.size() == 0);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenGetAllPageableNegativePage_thenUnprocessableEntityException() {
        List<Branch> found = branchService.getAllBranchesPageable(-10, 10);

        assert (found.size() == 0);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenGetAllPageableNegativeSize_thenUnprocessableEntityException() {
        List<Branch> found = branchService.getAllBranchesPageable(10, -10);

        assert (found.size() == 0);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenGetAllPageableNegativePageAndNegativeSize_thenUnprocessableEntityException() {
        List<Branch> found = branchService.getAllBranchesPageable(-10, -10);

        assert (found.size() == 0);
    }

    @Test
    public void whenGetAllPageable_thenCorrectSize() {
        List<Branch> found = branchService.getAllBranchesPageable(1, 1);

        assert (found.size() == 3);
    }

    @Test
    public void whenPageSize_thenPageAmountThree() {
        long pageSize = 1L;
        long amount = branchService.getBranchesListPagesAmount(pageSize);

        assert (amount == 3);
    }

    @Test
    public void whenPageSize_thenPageAmountTwo() {
        long pageSize = 2L;
        long amount = branchService.getBranchesListPagesAmount(pageSize);

        assert (amount == 2);
    }

    @Test
    public void whenPageSize_thenPageAmountOne() {
        long pageSize = 3L;
        long amount = branchService.getBranchesListPagesAmount(pageSize);

        assert (amount == 1);
    }

    @Test
    public void whenBigPageSize_thenPageAmountOne() {
        long pageSize = 10L;
        long amount = branchService.getBranchesListPagesAmount(pageSize);

        assert (amount == 1);
    }

    @Test
    public void whenPageSize_thenPageAmountZero() {
        long pageSize = -1L;
        long amount = branchService.getBranchesListPagesAmount(pageSize);

        assert (amount == 0);
    }


    @Test
    public void whenValidId_thenGetBranch() {
        long id = 1L;
        Branch found = branchService.getBranchById(id);

        assert (found.getId() == id);
    }

    @Test(expected = NotFoundException.class)
    public void whenNotValidId_thenNotFoundException() {
        long id = 10L;
        Branch found = branchService.getBranchById(id);

        assert (found == null);
    }

    @Test(expected = NotFoundException.class)
    public void whenNegativeId_thenNotFoundException() {
        long id = -1L;
        Branch found = branchService.getBranchById(id);

        assert (found == null);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenDeleteNotValid_thenUnprocessableEntityException() {
        long id = 10L;
        branchService.deleteBranchById(id);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenDeleteNegative_thenUnprocessableEntityException() {
        long id = -10L;
        branchService.deleteBranchById(id);
    }

    @Test
    public void whenDelete_thenNotFound() {
        long id = 1L;
        branchService.deleteBranchById(id);
        assert (true);
    }

    @Test
    public void whenCreate_thenBranchCreated() {
        BranchDto branchDto = new BranchDto();
        branchDto.setAddress("rrr");
        branchDto.setTelephone("1478526");

        CreateActionResponseDto responseDto = branchService.createBranch(branchDto);

        assert (responseDto.getId() == 0);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenUpdateNotValid_thenUnprocessableEntityException() {
        BranchDto branchDto = new BranchDto();
        branchDto.setId(100);
        branchDto.setAddress("rrr");
        branchDto.setTelephone("1478526");

        branchService.updateBranch(branchDto);
    }
}
