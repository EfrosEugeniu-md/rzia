package ru.list.sorfe.rzia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.list.sorfe.rzia.beans.station.TransformationCurrent;

@CrossOrigin(origins = "*")
@Repository
public interface TransRepository
        extends CrudRepository<TransformationCurrent, Long> {
}
