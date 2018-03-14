package by.bsuir.spp.jewelryrentsystem.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/")
public class DefaultController {
    @GetMapping(value = "/")
    public ModelAndView index(Model model) {
        model.addAttribute("message", "It works :) Ура!!!");
        return new ModelAndView("index");
    }
}
