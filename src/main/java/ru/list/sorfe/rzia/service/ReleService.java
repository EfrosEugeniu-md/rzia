package ru.list.sorfe.rzia.service;

import ru.list.sorfe.rzia.beans.station.Rely;

import java.util.List;

public interface ReleService {
    Rely save(Rely rele);

    List<Rely> findAll();
}
