package by.bsuir.spp.jewelryrentsystem.controller;

import by.bsuir.spp.jewelryrentsystem.dto.MaterialDto;
import by.bsuir.spp.jewelryrentsystem.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/material", produces = MediaType.APPLICATION_JSON_VALUE)
public class MaterialController {
    @Autowired
    private MaterialService materialService;

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
    public void deleteMaterial(@RequestParam(value = "id") long id) {
        materialService.deleteMaterialById(id);
    }

    @PostMapping(value = "/create")
    public void createMaterial(@Validated(MaterialDto.Create.class) @ModelAttribute MaterialDto order) {
        materialService.createMaterial(order);
    }

    @PostMapping(value = "/update")
    public void updateMaterial(@Validated(MaterialDto.Update.class) @ModelAttribute MaterialDto order) {
        materialService.updateMaterial(order);
    }
}
