package by.bsuir.spp.jewelryrentsystem.service.impl;

import by.bsuir.spp.jewelryrentsystem.service.PaginationService;
import org.springframework.stereotype.Service;

@Service
public class PaginationServiceImpl implements PaginationService {
    @Override
    public long getPagesAmount(long pageSize, long itemsAmount) {
        if (itemsAmount <= 0) {
            return 0;
        }

        if (pageSize <= 0) {
            return 0;
        }

        if (itemsAmount % pageSize == 0) {
            return itemsAmount / pageSize;
        } else {
            return (itemsAmount / pageSize) + 1;
        }
    }
}
