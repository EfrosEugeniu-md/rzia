package ru.list.sorfe.rzia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.list.sorfe.rzia.beans.station.Rely;
import ru.list.sorfe.rzia.beans.station.TransformationCurrent;
import ru.list.sorfe.rzia.repository.ReleRepository;
import ru.list.sorfe.rzia.service.ReleService;
import ru.list.sorfe.rzia.service.StationAndCellularService;
import ru.list.sorfe.rzia.util.StationUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReleServiceImpl implements ReleService {
    private final ReleRepository releRepository;

    @Autowired
    public ReleServiceImpl(ReleRepository releRepository, StationAndCellularService stationAndCellularService) {
        this.releRepository = releRepository;
//        try {
//            for (Rely rely : StationUtil.releList) {
//                rely.setStationAndCellular(
//                        stationAndCellularService.findById(rely.getId().getTpIc()));
//
//                releRepository.save(rely);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
