package ru.list.sorfe.rzia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.list.sorfe.rzia.beans.station.TransformationCurrent;
import ru.list.sorfe.rzia.repository.TransRepository;
import ru.list.sorfe.rzia.service.TransService;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransServiceImpl implements TransService {
    private final TransRepository transRepository;

    @Autowired
    public TransServiceImpl(TransRepository transRepository) {

        this.transRepository = transRepository;
    }

    @Override
    public TransformationCurrent save(TransformationCurrent trans) {
        return transRepository.save(trans);
    }

    @Override
    public List<TransformationCurrent> findAll() {
        List<TransformationCurrent> result = new ArrayList<>();
        transRepository.findAll().forEach(result::add);
        return result;
    }
}
