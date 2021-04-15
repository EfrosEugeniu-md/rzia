package ru.list.sorfe.rzia.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class BlogSingleController {
    @GetMapping(value = {"blog-single.html"})
    public ModelAndView viewHome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("blog-single");
        return modelAndView;
    }
}
