package by.bsuir.spp.jewelryrentsystem.controller;

import by.bsuir.spp.jewelryrentsystem.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/")
public class DefaultController {
    @Autowired
    private ClientService clientService;

    @GetMapping(value = "/")
    public ModelAndView index(Model model) {
        model.addAttribute("message", "It works :) Ура!!!");
        return new ModelAndView("index");
    }

    @GetMapping(value = "/clients")
    public ModelAndView clients(Model model) {
        model.addAttribute("clients", clientService.getAllClients());
        return new ModelAndView("clients");
    }
}
