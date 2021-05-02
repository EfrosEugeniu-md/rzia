package ru.list.sorfe.rzia.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.list.sorfe.rzia.service.StationAndCellularService;

@Controller
public class ListCellularController {
    @Autowired
    StationAndCellularService stationAndCellularService;

    @GetMapping(value = {"list-cellular.html"})
    public ModelAndView viewHome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("list-cellular");
        modelAndView.addObject("list", stationAndCellularService.findAll());
        return modelAndView;
    }
}
