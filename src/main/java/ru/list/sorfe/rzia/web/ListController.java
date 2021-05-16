package ru.list.sorfe.rzia.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.list.sorfe.rzia.beans.Station;
import ru.list.sorfe.rzia.service.StationAndCellularService;
import ru.list.sorfe.rzia.service.StationService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ListController {
    @Autowired
    StationService stationService;
    @Autowired
    StationAndCellularService stationAndCellularService;


    @GetMapping(value = {"list.html"})
    public ModelAndView viewHome() {
        ModelAndView modelAndView = new ModelAndView();

      //  modelAndView.addObject("list", stationService.findAll());
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

    @PostMapping(value = {"list.html"})
    public ModelAndView managementStation(HttpServletRequest request, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        // String action = request.getParameter(AppConstant.ASS_REQUEST);
        String action = "";
        Integer pageSize = null;
        try {
            // pageSize = Integer.valueOf(request.getParameter(AppConstant.PAGE_SIZE_PARAMETER));
        } catch (Exception ignored) {
        }
        switch (action != null ? action : "") {
            case "reset": {

                break;
            }
            case "search": {

                break;
            }
            case "reload": {

                break;
            }
            case "new": {

            }
            default: {
                break;
            }
        }
        modelAndView.setViewName("loss-management/loss");
//        modelAndView.addObject("list", smarrimentiService.findAll(paginator, form));
//        modelAndView.addObject("paginator", paginator);
//        modelAndView.addObject("form", form);
//        modelAndView.addObject("institutes", institutes);
        return modelAndView;
    }


/*    private SmarrimentiId transformId(String id) {
        SmarrimentiId lossId = new SmarrimentiId();
        try {
            String[] fields = id.split("_");
            lossId.setIstituto(fields[0]);
            lossId.setNumPratica(Long.valueOf(fields[1]));
            lossId.setProgressivo(Long.valueOf(fields[2]));
        } catch (Exception ignored) {}
        return lossId;
    }
 <td style="width: 10px;">
                                        <a th:href="@{'/loss/edit/' + ${loss.id.istituto} + '_' + ${loss.id.numPratica} + '_' + ${loss.id.progressivo}}">
                                            <i class="fa fa-pencil-square-o" aria-hidden="true" title="Edit"></i>
                                        </a>
                                    </td>*/


}
