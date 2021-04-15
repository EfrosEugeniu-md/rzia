package ru.list.sorfe.rzia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.list.sorfe.rzia.beans.TpIc;
import ru.list.sorfe.rzia.beans.station.StationAndCellular;

@Repository
public interface StationAndCellularRepository  extends CrudRepository<StationAndCellular, TpIc> {
}
