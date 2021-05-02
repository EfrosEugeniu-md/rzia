package ru.list.sorfe.rzia.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.list.sorfe.rzia.service.ProviderService;

@Controller
public class ListProviderController {
    @Autowired
    ProviderService providerService;

    @GetMapping(value = {"list-provider.html"})
    public ModelAndView viewHome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("list-provider");
        modelAndView.addObject("list", providerService.findAll());
        return modelAndView;
    }
}
