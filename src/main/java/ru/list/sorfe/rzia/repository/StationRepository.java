package ru.list.sorfe.rzia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.list.sorfe.rzia.beans.Station;

@Repository
public interface StationRepository
        extends CrudRepository<Station, Integer> {
}
