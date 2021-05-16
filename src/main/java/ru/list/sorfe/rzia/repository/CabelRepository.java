package ru.list.sorfe.rzia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.list.sorfe.rzia.beans.TpIc;
import ru.list.sorfe.rzia.beans.consumer.Cabel;

import java.util.Optional;

@CrossOrigin(origins = "*")
@RepositoryRestResource(collectionResourceRel = "Cables", path = "Cables")
public interface CabelRepository
        extends CrudRepository<Cabel, Long> {
    Optional<Cabel> findFirstByTpIcConsumer(TpIc tpIcConsumer);
}
