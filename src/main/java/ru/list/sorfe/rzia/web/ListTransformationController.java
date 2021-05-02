package ru.list.sorfe.rzia.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.list.sorfe.rzia.service.TransService;

@Controller
public class ListTransformationController {
    @Autowired
    TransService transService;

    @GetMapping(value = {"list-transformation.html"})
    public ModelAndView viewHome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("list-transformation");
        modelAndView.addObject("list", transService.findAll());
        return modelAndView;
    }
}
