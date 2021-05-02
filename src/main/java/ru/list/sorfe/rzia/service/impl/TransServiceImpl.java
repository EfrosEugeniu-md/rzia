package ru.list.sorfe.rzia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.list.sorfe.rzia.beans.Station;
import ru.list.sorfe.rzia.beans.station.TransformationCurrent;
import ru.list.sorfe.rzia.repository.TransRepository;
import ru.list.sorfe.rzia.service.StationAndCellularService;
import ru.list.sorfe.rzia.service.TransService;
import ru.list.sorfe.rzia.util.StationUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransServiceImpl implements TransService {
    private final TransRepository transRepository;

    @Autowired
    public TransServiceImpl(TransRepository transRepository, StationAndCellularService stationAndCellularService) {

        this.transRepository = transRepository;
        for (TransformationCurrent transformationCurrent : StationUtil.transList){
            transformationCurrent.setStationAndCellular(
                    stationAndCellularService.findById(transformationCurrent.getId().getTpIc()));
            transRepository.save(transformationCurrent);
        }

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
