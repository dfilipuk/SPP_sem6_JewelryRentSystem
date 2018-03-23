package by.bsuir.spp.jewelryrentsystem.controller;

import by.bsuir.spp.jewelryrentsystem.dto.CreateActionResponseDto;
import by.bsuir.spp.jewelryrentsystem.dto.DeleteActionRequestDto;
import by.bsuir.spp.jewelryrentsystem.dto.JewelryDto;
import by.bsuir.spp.jewelryrentsystem.service.JewelryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/jewelry", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class JewelryController {
    private final JewelryService jewelryService;

    @Autowired
    public JewelryController(JewelryService jewelryService) {
        this.jewelryService = jewelryService;
    }

    @GetMapping(value = "/list")
    public List<JewelryDto> getAllJewelries() {
        return jewelryService.getAllJewelries();
    }

    @GetMapping(value = "/page-list")
    public List<JewelryDto> getAllJewelriesPageable(@RequestParam(value = "page") int pageNumber,
                                                    @RequestParam(value = "page-size") int pageSize) {
        return jewelryService.getAllJewelriesPageable(pageNumber, pageSize);
    }

    @GetMapping(value = "/list-pages-amo")
    public long getPagesAmount(@RequestParam(value = "page-size") long pageSize) {
        return jewelryService.getJewelriesListPagesAmount(pageSize);
    }

    @GetMapping(value = "/get")
    public JewelryDto getJewelry(@RequestParam(value = "id") long id) {
        return jewelryService.getJewelryById(id);
    }

    @PostMapping(value = "/delete")
    public void deleteJewelry(@Validated @RequestBody DeleteActionRequestDto deleteActionRequestDto) {
        jewelryService.deleteJewelryById(deleteActionRequestDto.getId());
    }

    @PostMapping(value = "/create")
    public CreateActionResponseDto createJewelry(@Validated(JewelryDto.Create.class) @RequestBody JewelryDto jewelry) {
        return jewelryService.createJewelry(jewelry);
    }

    @PostMapping(value = "/update")
    public void updateJewelry(@Validated(JewelryDto.Update.class) @RequestBody JewelryDto jewelry) {
        jewelryService.updateJewelry(jewelry);
    }
}
