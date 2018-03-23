package by.bsuir.spp.jewelryrentsystem.service.impl;

import by.bsuir.spp.jewelryrentsystem.dto.CreateActionResponseDto;
import by.bsuir.spp.jewelryrentsystem.model.Branch;
import by.bsuir.spp.jewelryrentsystem.dto.BranchDto;
import by.bsuir.spp.jewelryrentsystem.repository.BranchRepository;
import by.bsuir.spp.jewelryrentsystem.service.BranchService;
import by.bsuir.spp.jewelryrentsystem.service.PaginationService;
import by.bsuir.spp.jewelryrentsystem.service.exception.InternalServerErrorException;
import by.bsuir.spp.jewelryrentsystem.service.exception.NotFoundException;
import by.bsuir.spp.jewelryrentsystem.service.exception.UnprocessableEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {
    private final String SORT_COLUMN = "address";

    private final BranchRepository branchRepository;
    private final PaginationService paginationService;

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository, PaginationService paginationService) {
        this.branchRepository = branchRepository;
        this.paginationService = paginationService;
    }

    @Override
    public List<Branch> getAllBranches() {
        List<Branch> result;

        try {
            Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, SORT_COLUMN));
            result = branchRepository.findAll(sort);
        } catch (Exception e) {
            throw new UnprocessableEntityException(e.getMessage());
        }

        return result;
    }

    @Override
    public List<Branch> getAllBranchesPageable(int page, int size) {
        List<Branch> result;

        try {
            Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, SORT_COLUMN));
            Pageable pageable = new PageRequest(page, size, sort);
            result = branchRepository.findAll(pageable).getContent();
        } catch (Exception e) {
            throw new UnprocessableEntityException(e.getMessage());
        }

        return result;
    }

    @Override
    public long getBranchesListPagesAmount(long pageSize) {
        return paginationService.getPagesAmount(pageSize, branchRepository.count());
    }

    @Override
    public Branch getBranchById(long id) {
        Branch branch = branchRepository.findOne(id);

        if (branch == null) {
            throw new NotFoundException("Branch not found");
        }

        return branch;
    }

    @Override
    public void deleteBranchById(long id) {
        Branch branch = branchRepository.findOne(id);

        if (branch == null) {
            throw new UnprocessableEntityException("Branch for delete not found");
        }

        if (!branch.getEmployees().isEmpty()) {
            throw new UnprocessableEntityException("Unable to delete branch with associated employees");
        }

        if (!branch.getJewelries().isEmpty()) {
            throw new UnprocessableEntityException("Unable to delete branch with associated jewelries");
        }

        try {
            branchRepository.delete(branch);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public CreateActionResponseDto createBranch(BranchDto branchDto) {
        Branch branch = new Branch();
        saveBranchData(branch, branchDto);
        return new CreateActionResponseDto(branch.getId());
    }

    @Override
    public void updateBranch(BranchDto branchDto) {
        Branch branch = branchRepository.findOne(branchDto.getId());

        if (branch == null) {
            throw new UnprocessableEntityException("Branch for update not found");
        }

        saveBranchData(branch, branchDto);
    }

    private void saveBranchData(Branch branch, BranchDto branchDto) {
        branch.setAddress(branchDto.getAddress());
        branch.setTelephone(branchDto.getTelephone());

        try {
            branchRepository.saveAndFlush(branch);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
