package ru.list.sorfe.rzia.service;

import ru.list.sorfe.rzia.beans.Station;
import ru.list.sorfe.rzia.beans.TpIc;
import ru.list.sorfe.rzia.beans.consumer.Provider;
import ru.list.sorfe.rzia.beans.station.StationAndCellular;

import java.util.List;

public interface StationAndCellularService {
    StationAndCellular save(StationAndCellular stationAndCellular);

    StationAndCellular findById(TpIc id);

    List<StationAndCellular> findAll();

   String getNumberAndProtectedElement(StationAndCellular stationAndCellular);

    String getProtectedObject(StationAndCellular stationAndCellular);

    String getTypeCell(Station station);

    String getCurentIntrerupere(StationAndCellular stationAndCellular);

    String getSupracurent(StationAndCellular stationAndCellular);

    int getPowerTrans(Provider provider);

    String[][] getTableSecond(StationAndCellular stationAndCellular);

    String[][] getTableFourth(StationAndCellular stationAndCellular);

    String[][] getTableFifth(StationAndCellular stationAndCellular);
}
