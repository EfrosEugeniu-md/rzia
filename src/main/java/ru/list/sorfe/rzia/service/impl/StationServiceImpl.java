package ru.list.sorfe.rzia.service.impl;

import org.springframework.stereotype.Service;
import ru.list.sorfe.rzia.beans.Station;
import ru.list.sorfe.rzia.repository.StationRepository;
import ru.list.sorfe.rzia.service.StationService;
import ru.list.sorfe.rzia.util.StationUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class StationServiceImpl implements StationService {
    private  final StationRepository stationRepository;

    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
        for (Station station : StationUtil.stHashSet){
            stationRepository.save(station);
    }}

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
        List<Station> result = new ArrayList<>();
        stationRepository.findAll().forEach(result::add);
        return result;
    }
}
