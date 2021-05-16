package ru.list.sorfe.rzia.service;

import ru.list.sorfe.rzia.beans.consumer.Cabel;

import java.util.List;

public interface CabelService {
    Cabel save(Cabel cabel);

    List<Cabel> findAll();
}
