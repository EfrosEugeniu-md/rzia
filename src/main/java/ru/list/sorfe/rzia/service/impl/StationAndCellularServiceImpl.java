package ru.list.sorfe.rzia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.list.sorfe.rzia.beans.TpIc;
import ru.list.sorfe.rzia.beans.station.StationAndCellular;
import ru.list.sorfe.rzia.repository.StationAndCellularRepository;
import ru.list.sorfe.rzia.service.StationAndCellularService;
import ru.list.sorfe.rzia.service.StationService;
import ru.list.sorfe.rzia.util.StationUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class StationAndCellularServiceImpl implements StationAndCellularService {
    private final StationAndCellularRepository stationAndCellularRepository;

    @Autowired
    public StationAndCellularServiceImpl(StationAndCellularRepository stationAndCellularRepository,
                                         StationService stationService) {
        this.stationAndCellularRepository = stationAndCellularRepository;
        for (StationAndCellular stationAndCellular : StationUtil.stCellHashSet) {
            stationAndCellular.setStation(
                    stationService.findById(stationAndCellular.getId().getTpNumber()));
            this.stationAndCellularRepository.save(stationAndCellular);
        }

    }

    @Override
    public StationAndCellular save(StationAndCellular stationAndCellular) {
        return stationAndCellularRepository.save(stationAndCellular);
    }

    @Override
    public StationAndCellular findById(TpIc id) {
        return stationAndCellularRepository.findById(id).orElse(null);
    }

    @Override
    public List<StationAndCellular> findAll() {
        List<StationAndCellular> result = new ArrayList<>();
        stationAndCellularRepository.findAll().forEach(result::add);
        return result;
    }
}
