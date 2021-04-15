package ru.list.sorfe.rzia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.list.sorfe.rzia.beans.TpIcPh;
import ru.list.sorfe.rzia.beans.station.TransformationCurrent;

@Repository
public interface TransRepository extends CrudRepository<TransformationCurrent, TpIcPh> {
}
