package ru.list.sorfe.rzia.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.list.sorfe.rzia.service.ConsumerService;

@Controller
public class ListConsumerController {
    @Autowired
    ConsumerService consumerService;

    @GetMapping(value = {"list-consumer.html"})
    public ModelAndView viewHome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("list-consumer");
      //  modelAndView.addObject("list", consumerService.findAll());
        return modelAndView;
    }
}
