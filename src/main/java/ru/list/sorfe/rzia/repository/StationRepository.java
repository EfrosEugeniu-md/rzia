package ru.list.sorfe.rzia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.list.sorfe.rzia.beans.Station;

@CrossOrigin(origins = "*")
@Repository
public interface StationRepository
        extends CrudRepository<Station, Integer> {
}
