package ru.list.sorfe.rzia.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.list.sorfe.rzia.repository.SetOfDocumentsRepository;
import ru.list.sorfe.rzia.service.ProviderService;
@Controller
public class ListDocumentController {
    @Autowired
    SetOfDocumentsRepository setOfDocumentsRepository;

    @GetMapping(value = {"list-document.html"})
    public ModelAndView viewHome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("list-document");
        modelAndView.addObject("list", setOfDocumentsRepository.findAll());
        return modelAndView;
    }
}
