package by.bsuir.spp.jewelryrentsystem.service;

import by.bsuir.spp.jewelryrentsystem.model.Branch;
import by.bsuir.spp.jewelryrentsystem.dto.BranchDto;

import java.util.List;

public interface BranchService {
    List<Branch> getAllBranches();
    List<Branch> getAllBranchesPageable(int page, int size);
    long getBranchesListPagesAmount(long pageSize);
    void deleteBranchById(long id);
    Branch getBranchById(long id);
    void createBranch(BranchDto branchDto);
    void updateBranch(BranchDto branchDto);
}
