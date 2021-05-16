package ru.list.sorfe.rzia.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.list.sorfe.rzia.beans.Station;
import ru.list.sorfe.rzia.beans.consumer.Cabel;
import ru.list.sorfe.rzia.beans.consumer.Consumer;
import ru.list.sorfe.rzia.beans.consumer.Provider;
import ru.list.sorfe.rzia.beans.station.Rely;
import ru.list.sorfe.rzia.beans.station.StationAndCellular;
import ru.list.sorfe.rzia.beans.station.TransformationCurrent;
import ru.list.sorfe.rzia.repository.*;
import ru.list.sorfe.rzia.service.StationService;
import ru.list.sorfe.rzia.util.ConsumerUtil;
import ru.list.sorfe.rzia.util.StationUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class StationServiceImpl implements StationService {
    @Value("${message.default.initialization:false}")
    private boolean initialization;
    private final StationRepository stationRepository;
    private final StationAndCellularRepository stationAndCellularRepository;
    private final ConsumerRepository consumerRepository;
    private final ProviderRepository providerRepository;
    private final CabelRepository cabelRepository;
    private final ReleRepository releRepository;
    private final TransRepository transRepository;

    public StationServiceImpl(StationRepository stationRepository,
                              StationAndCellularRepository stationAndCellularRepository,
                              ConsumerRepository consumerRepository,
                              ProviderRepository providerRepository,
                              CabelRepository cabelRepository,
                              ReleRepository releRepository,
                              TransRepository transRepository) {
        this.stationRepository = stationRepository;
        this.stationAndCellularRepository = stationAndCellularRepository;
        this.consumerRepository = consumerRepository;
        this.providerRepository = providerRepository;
        this.cabelRepository = cabelRepository;
        this.releRepository = releRepository;
        this.transRepository = transRepository;

        if (initialization) {
            initialization();
        }
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

            Cabel cabel = cabelRepository.findFirstByTpIcConsumer(consumer.getTpIc()).orElse(null);
            Provider provider = providerRepository.findFirstByTpIc(cabel.getTpIcProvider()).orElse(null);
            consumer.setCabel(cabel);
            consumer.setProvider(provider);
            consumerRepository.save(consumer);
        }

        for (StationAndCellular myStationAndCellular : StationUtil.stCellHashSet) {
            myStationAndCellular.setStation(
                    this.findById(myStationAndCellular.getTpIc().getTpNumber()));
            stationAndCellularRepository.save(myStationAndCellular);
        }

        for (Rely rely : StationUtil.releList) {
            rely.setStationAndCellular(
                    stationAndCellularRepository.findFirstByTpIc(rely.getTpIcPh().getTpIc()).orElse(null));

            releRepository.save(rely);
        }
        for (TransformationCurrent transformationCurrent : StationUtil.transList) {
            transformationCurrent.setStationAndCellular(
                    stationAndCellularRepository.findFirstByTpIc(transformationCurrent.getTpIcPh().getTpIc()).orElse(null));
            transRepository.save(transformationCurrent);
        }
    }
}
