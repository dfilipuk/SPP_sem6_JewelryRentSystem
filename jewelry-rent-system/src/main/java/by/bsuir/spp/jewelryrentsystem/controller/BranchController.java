package by.bsuir.spp.jewelryrentsystem.controller;

import by.bsuir.spp.jewelryrentsystem.dto.CreateActionResponseDto;
import by.bsuir.spp.jewelryrentsystem.model.Branch;
import by.bsuir.spp.jewelryrentsystem.dto.BranchDto;
import by.bsuir.spp.jewelryrentsystem.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/branch", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class BranchController {
    private final BranchService branchService;

    @Autowired
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping(value = "/list")
    public List<Branch> getAllBranches() {
        return branchService.getAllBranches();
    }

    @GetMapping(value = "/page-list")
    public List<Branch> getAllBranchesPageable(@RequestParam(value = "page") int pageNumber,
                                              @RequestParam(value = "page-size") int pageSize) {
        return branchService.getAllBranchesPageable(pageNumber, pageSize);
    }

    @GetMapping(value = "/list-pages-amo")
    public long getPagesAmount(@RequestParam(value = "page-size") long pageSize) {
        return branchService.getBranchesListPagesAmount(pageSize);
    }

    @GetMapping(value = "/get")
    public Branch getBranch(@RequestParam(value = "id") long id) {
        return branchService.getBranchById(id);
    }

    @PostMapping(value = "/delete")
    public void deleteBranch(@RequestParam(value = "id") long id) {
        branchService.deleteBranchById(id);
    }

    @PostMapping(value = "/create")
    public CreateActionResponseDto createBranch(@Validated(BranchDto.Create.class) @ModelAttribute BranchDto branch) {
        return branchService.createBranch(branch);
    }

    @PostMapping(value = "/update")
    public void updateBranch(@Validated(BranchDto.Update.class) @ModelAttribute BranchDto branch) {
        branchService.updateBranch(branch);
    }
}
