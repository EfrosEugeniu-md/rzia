package ru.list.sorfe.rzia.service;

import ru.list.sorfe.rzia.beans.station.TransformationCurrent;

import java.util.List;

public interface TransService {
    TransformationCurrent save(TransformationCurrent trans);

    List<TransformationCurrent> findAll();
}
