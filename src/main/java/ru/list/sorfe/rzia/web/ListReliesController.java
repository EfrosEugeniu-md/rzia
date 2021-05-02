package ru.list.sorfe.rzia.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.list.sorfe.rzia.service.ReleService;

@Controller
public class ListReliesController {
    @Autowired
    ReleService releService;

    @GetMapping(value = {"list-relies.html"})
    public ModelAndView viewHome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("list-relies");
        modelAndView.addObject("list", releService.findAll());
        return modelAndView;
    }
}
