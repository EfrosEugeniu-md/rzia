package ru.list.sorfe.rzia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.list.sorfe.rzia.beans.TpIc;
import ru.list.sorfe.rzia.beans.consumer.Cabel;

@Repository
public interface CabelRepository
        extends CrudRepository<Cabel, TpIc> {

}
