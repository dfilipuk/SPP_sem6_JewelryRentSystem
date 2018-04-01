package by.bsuir.spp.jewelryrentsystem.controller;

import by.bsuir.spp.jewelryrentsystem.dto.CreateActionResponseDto;
import by.bsuir.spp.jewelryrentsystem.dto.DeleteActionRequestDto;
import by.bsuir.spp.jewelryrentsystem.dto.MaterialDto;
import by.bsuir.spp.jewelryrentsystem.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/material", produces = MediaType.APPLICATION_JSON_VALUE)
public class MaterialController {
    private final MaterialService materialService;

    @Autowired
    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping(value = "/list")
    public List<MaterialDto> getAllMaterials() {
        return materialService.getAllMaterials();
    }

    @GetMapping(value = "/page-list")
    public List<MaterialDto> getAllMaterialsPageable(@RequestParam(value = "page") int pageNumber,
                                               @RequestParam(value = "page-size") int pageSize) {
        return materialService.getAllMaterialsPageable(pageNumber, pageSize);
    }

    @GetMapping(value = "/list-pages-amo")
    public long getPagesAmount(@RequestParam(value = "page-size") long pageSize) {
        return materialService.getMaterialsListPagesAmount(pageSize);
    }

    @GetMapping(value = "/get")
    public MaterialDto getMaterial(@RequestParam(value = "id") long id) {
        return materialService.getMaterialById(id);
    }

    @PostMapping(value = "/delete")
    public void deleteMaterial(@Validated @RequestBody DeleteActionRequestDto deleteActionRequestDto) {
        materialService.deleteMaterialById(deleteActionRequestDto.getId());
    }

    @PostMapping(value = "/create")
    public CreateActionResponseDto createMaterial(@Validated(MaterialDto.Create.class) @RequestBody MaterialDto material) {
        return materialService.createMaterial(material);
    }

    @PostMapping(value = "/update")
    public void updateMaterial(@Validated(MaterialDto.Update.class) @RequestBody MaterialDto material) {
        materialService.updateMaterial(material);
    }
}
