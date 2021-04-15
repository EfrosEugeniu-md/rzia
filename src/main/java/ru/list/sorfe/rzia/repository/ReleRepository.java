package ru.list.sorfe.rzia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.list.sorfe.rzia.beans.TpIcPh;
import ru.list.sorfe.rzia.beans.station.Rely;

@Repository
public interface ReleRepository extends CrudRepository<Rely, TpIcPh> {
}
