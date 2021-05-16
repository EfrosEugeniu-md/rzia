package ru.list.sorfe.rzia.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.list.sorfe.rzia.beans.TpIc;
import ru.list.sorfe.rzia.beans.consumer.Consumer;
import ru.list.sorfe.rzia.service.ConsumerService;


@RestController
@RequestMapping(value = "/consumers")
public class ConsumerRestController {

    @Autowired
    ConsumerService consumerService;

    @GetMapping(value = "/{id}")
    public Consumer getAllBranch(@PathVariable("id") String id) {
        try {
            TpIc tpIc = TpIc.toTpIC(id);
           // return consumerService.findById(tpIc).orElse(null);
            return null;
        } catch (Exception e) {

        }
        return null;
    }
}
