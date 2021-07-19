package ru.list.sorfe.rzia.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.list.sorfe.rzia.repository.*;

@Controller
public class HomeController {
    private final StationRepository stationRepository;
    private final StationAndCellularRepository stationAndCellularRepository;
    private final ReleRepository releRepository;
    private final TransRepository transRepository;

    public HomeController(StationRepository stationRepository, StationAndCellularRepository stationAndCellularRepository, ConsumerRepository consumerRepository, ProviderRepository providerRepository, CabelRepository cabelRepository, ReleRepository releRepository, TransRepository transRepository) {
        this.stationRepository = stationRepository;
        this.stationAndCellularRepository = stationAndCellularRepository;
        this.releRepository = releRepository;
        this.transRepository = transRepository;
    }

    @GetMapping(value = {"", "/", "/home", "/index.html", "/nhgf"})
    public ModelAndView viewHome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("stationCount", stationRepository.count());
        modelAndView.addObject("cellularCount", stationAndCellularRepository.count());
        modelAndView.addObject("releCount", releRepository.count());
        modelAndView.addObject("transCount", transRepository.count());
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
