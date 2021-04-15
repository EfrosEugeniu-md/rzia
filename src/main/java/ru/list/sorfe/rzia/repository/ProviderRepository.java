package ru.list.sorfe.rzia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.list.sorfe.rzia.beans.consumer.Provider;

@Repository
public interface ProviderRepository extends CrudRepository<Provider, Long> {
}
