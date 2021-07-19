package ru.list.sorfe.rzia.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import ru.list.sorfe.rzia.beans.Station;
import ru.list.sorfe.rzia.service.StationService;

@Controller
public class ListController {
    private final    StationService stationService;

    public ListController(StationService stationService) {
        this.stationService = stationService;
    }


    @GetMapping(value = {"list.html"})
    public ModelAndView viewHome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("list", stationService.findAll());
        modelAndView.setViewName("list");
        return modelAndView;
    }

    @GetMapping(value = "/list.html/{id}")
    public ModelAndView viewLoss(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView();
        Station station = stationService.findById(Integer.parseInt(id));
        modelAndView.addObject("station", station);
        modelAndView.setViewName("list-detail");
        return modelAndView;
    }
}
