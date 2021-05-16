package ru.list.sorfe.rzia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.list.sorfe.rzia.beans.consumer.Consumer;

@CrossOrigin(origins = "*")
@Repository
public interface ConsumerRepository
        extends CrudRepository<Consumer, Long> {

}
