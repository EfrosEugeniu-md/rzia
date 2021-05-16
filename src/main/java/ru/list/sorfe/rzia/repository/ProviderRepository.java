package ru.list.sorfe.rzia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.list.sorfe.rzia.beans.TpIc;
import ru.list.sorfe.rzia.beans.consumer.Provider;

import java.util.Optional;

@CrossOrigin(origins = "*")
@Repository
public interface ProviderRepository
        extends CrudRepository<Provider, Long> {
    Optional<Provider> findFirstByTpIc(TpIc tpIc);
}
