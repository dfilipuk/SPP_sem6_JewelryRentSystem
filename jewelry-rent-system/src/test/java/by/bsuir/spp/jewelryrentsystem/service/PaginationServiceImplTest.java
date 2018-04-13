package by.bsuir.spp.jewelryrentsystem.service;

import by.bsuir.spp.jewelryrentsystem.service.impl.PaginationServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PaginationServiceImplTest {
    @TestConfiguration
    static class PaginationServiceImplTestContextConfiguration {

        @Bean
        public PaginationService paginationService() {
            return new PaginationServiceImpl();
        }
    }

    @Autowired
    private PaginationService paginationService;

    @Test
    public void whenValid_thenPageAmount() {
        long amount = paginationService.getPagesAmount(2, 10);
        assert (amount == 5);
    }

    @Test
    public void whenValid_thenPageAmountCorrect() {
        long amount = paginationService.getPagesAmount(20, 10);
        assert (amount == 1);
    }

    @Test
    public void whenEqual_thenOnePageAmount() {
        long amount = paginationService.getPagesAmount(50, 50);
        assert (amount == 1);
    }

    @Test
    public void whenValid_thenMorePagesAmount() {
        long amount = paginationService.getPagesAmount(3, 10);
        assert (amount == 4);
    }

    @Test
    public void whenMinValidEqual_thenOnePageAmount() {
        long amount = paginationService.getPagesAmount(1, 1);
        assert (amount == 1);
    }

    @Test
    public void whenMaxValidEqual_thenOnePageAmount() {
        long amount = paginationService.getPagesAmount(Long.MAX_VALUE, Long.MAX_VALUE);
        assert (amount == 1);
    }

    @Test
    public void whenMinEqual_thenZeroPageAmount() {
        long amount = paginationService.getPagesAmount(Long.MIN_VALUE, Long.MIN_VALUE);
        assert (amount == 0);
    }

    @Test
    public void whenZeroSize_thenZeroPagesAmount() {
        long amount = paginationService.getPagesAmount(0, 10);
        assert (amount == 0);
    }

    @Test
    public void whenZeroAmount_thenZeroPagesAmount() {
        long amount = paginationService.getPagesAmount(10, 0);
        assert (amount == 0);
    }

    @Test
    public void whenZeroSizeAndZeroAmount_thenZeroPagesAmount() {
        long amount = paginationService.getPagesAmount(0, 10);
        assert (amount == 0);
    }

    @Test
    public void whenNegativeSize_thenZeroPagesAmount() {
        long amount = paginationService.getPagesAmount(-5, 10);
        assert (amount == 0);
    }

    @Test
    public void whenNegativeAmount_thenZeroPagesAmount() {
        long amount = paginationService.getPagesAmount(10, -5);
        assert (amount == 0);
    }

    @Test
    public void whenNegativeSizeAndNegativeAmount_thenZeroPagesAmount() {
        long amount = paginationService.getPagesAmount(-5, -5);
        assert (amount == 0);
    }

    @Test
    public void whenZeroSizeAndNegativeAmount_thenZeroPagesAmount() {
        long amount = paginationService.getPagesAmount(0, -5);
        assert (amount == 0);
    }

    @Test
    public void whenNegativeSizeAndZeroAmount_thenZeroPagesAmount() {
        long amount = paginationService.getPagesAmount(-5, 0);
        assert (amount == 0);
    }
}
