package ru.list.sorfe.rzia.service;

import ru.list.sorfe.rzia.beans.Station;

import java.util.List;

public interface StationService {
    Station save(Station trans);
    Station findById(Integer id);
    List<Station> findAll();
}
