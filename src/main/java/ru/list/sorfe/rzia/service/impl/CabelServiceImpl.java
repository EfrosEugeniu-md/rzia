package ru.list.sorfe.rzia.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.list.sorfe.rzia.beans.consumer.Cabel;
import ru.list.sorfe.rzia.repository.CabelRepository;
import ru.list.sorfe.rzia.service.CabelService;
import ru.list.sorfe.rzia.util.ConsumerUtil;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CabelServiceImpl implements CabelService {

    private final CabelRepository repository;

    @Autowired
    public CabelServiceImpl(CabelRepository repository) {
        this.repository = repository;
//        for (Cabel cabel : ConsumerUtil.cabels) repository.save(cabel);

    }

    @Override
    public Cabel save(Cabel cabel) {
        return repository.save(cabel);
    }

    @Override
    public List<Cabel> findAll() {
        List<Cabel> result = new ArrayList<Cabel>();
        repository.findAll().forEach(result::add);
        return result;
    }
}
