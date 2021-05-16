package ru.list.sorfe.rzia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.list.sorfe.rzia.beans.station.Rely;

@CrossOrigin(origins = "*")
@Repository
public interface ReleRepository
        extends CrudRepository<Rely, Long> {
}
