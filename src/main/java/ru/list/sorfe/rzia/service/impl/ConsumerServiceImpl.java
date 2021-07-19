package ru.list.sorfe.rzia.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.list.sorfe.rzia.beans.TpIc;
import ru.list.sorfe.rzia.beans.consumer.Consumer;
import ru.list.sorfe.rzia.repository.ConsumerRepository;
import ru.list.sorfe.rzia.service.ConsumerService;
import ru.list.sorfe.rzia.service.StationService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ConsumerServiceImpl implements ConsumerService {
    private final ConsumerRepository repository;

    @Autowired
    public ConsumerServiceImpl(ConsumerRepository repository, StationService stationService) {
        this.repository = repository;
    }

    @Override
    public Consumer save(Consumer consumer) {
        return repository.save(consumer);
    }

    @Override
    public List<Consumer> findAll() {
        List<Consumer> result = new ArrayList<Consumer>();
        repository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public Consumer findByTpIc(TpIc tpIc) {
        return repository.findByTpIc(tpIc);
    }
}
