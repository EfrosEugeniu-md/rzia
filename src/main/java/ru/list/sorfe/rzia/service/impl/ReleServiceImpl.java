package ru.list.sorfe.rzia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.list.sorfe.rzia.beans.station.Rely;
import ru.list.sorfe.rzia.repository.ReleRepository;
import ru.list.sorfe.rzia.service.ReleService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReleServiceImpl implements ReleService {
    private final ReleRepository releRepository;

    @Autowired
    public ReleServiceImpl(ReleRepository releRepository) {
        this.releRepository = releRepository;
    }

    @Override
    public Rely save(Rely trans) {
        return releRepository.save(trans);
    }

    @Override
    public List<Rely> findAll() {
        List<Rely> result = new ArrayList<>();
        releRepository.findAll().forEach(result::add);
        return result;
    }
}
