package ru.list.sorfe.rzia.service.impl;

import org.springframework.stereotype.Service;
import ru.list.sorfe.rzia.beans.Station;
import ru.list.sorfe.rzia.beans.consumer.Cabel;
import ru.list.sorfe.rzia.beans.consumer.Consumer;
import ru.list.sorfe.rzia.beans.consumer.Provider;
import ru.list.sorfe.rzia.beans.station.Rely;
import ru.list.sorfe.rzia.beans.station.StationAndCellular;
import ru.list.sorfe.rzia.beans.station.TransformationCurrent;
import ru.list.sorfe.rzia.repository.*;
import ru.list.sorfe.rzia.service.StationAndCellularService;
import ru.list.sorfe.rzia.service.StationService;
import ru.list.sorfe.rzia.util.ConsumerUtil;
import ru.list.sorfe.rzia.util.StationUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class StationServiceImpl implements StationService {
    private final StationRepository stationRepository;
    private final StationAndCellularRepository stationAndCellularRepository;
    private final StationAndCellularService stationAndCellularService;
    private final ConsumerRepository repository;
    private final ProviderRepository providerRepository;
    private final CabelRepository cabelRepository;
    private final ReleRepository releRepository;
    private final TransRepository transRepository;

    public StationServiceImpl(StationRepository stationRepository, StationAndCellularRepository stationAndCellularRepository, StationAndCellularService stationAndCellularService, ConsumerRepository repository, ProviderRepository providerRepository, CabelRepository cabelRepository, ReleRepository releRepository, TransRepository transRepository) {
        this.stationRepository = stationRepository;
        this.stationAndCellularRepository = stationAndCellularRepository;
        this.stationAndCellularService = stationAndCellularService;
//        for (Station station : StationUtil.stHashSet) {
//            stationRepository.save(station);
//        }
        this.repository = repository;
        this.providerRepository = providerRepository;
        this.cabelRepository = cabelRepository;
        this.releRepository = releRepository;
        this.transRepository = transRepository;
    }

    @Override
    public Station save(Station st) {
        return stationRepository.save(st);
    }

    @Override
    public Station findById(Integer id) {
        return stationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Station> findAll() {
        if (stationRepository.count() == 0) initialization();
        List<Station> result = new ArrayList<>();
        stationRepository.findAll().forEach(result::add);
        return result;
    }

    private void initialization() {

        for (Station station : StationUtil.stHashSet) {
            stationRepository.save(station);
        }

        for (Cabel cabel : ConsumerUtil.cabels) {
            cabelRepository.save(cabel);
        }

        for (Provider provider : ConsumerUtil.providers) {
            providerRepository.save(provider);
        }

        for (Consumer consumer : ConsumerUtil.consumers) {
            consumer.setStation(
                    this.findById(consumer.getTpIc().getTpNumber()));
            repository.save(consumer);
        }

        for (StationAndCellular myStationAndCellular : StationUtil.stCellHashSet) {
            myStationAndCellular.setStation(
                    this.findById(myStationAndCellular.getId().getTpNumber()));
            stationAndCellularRepository.save(myStationAndCellular);
        }

        for (Rely rely : StationUtil.releList) {
            rely.setStationAndCellular(
                    stationAndCellularService.findById(rely.getId().getTpIc()));

            releRepository.save(rely);
        }
        for (TransformationCurrent transformationCurrent : StationUtil.transList) {
            transformationCurrent.setStationAndCellular(
                    stationAndCellularService.findById(transformationCurrent.getId().getTpIc()));
            transRepository.save(transformationCurrent);
        }
    }
}
