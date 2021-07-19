package ru.list.sorfe.rzia.repository;

import org.springframework.data.repository.CrudRepository;
import ru.list.sorfe.rzia.beans.consumer.Consumer;
import ru.list.sorfe.rzia.beans.station.Personal;

import java.util.Optional;

public interface PersonalRepository extends CrudRepository<Personal, Long> {
    Optional<Personal> findFirstByTypeOfPosition(Personal.TypeOfPosition typeOfPosition);
    Optional<Personal> findFirstByTypeOfPositionAndTypeOfServiceZonal(Personal.TypeOfPosition typeOfPosition, Consumer.TypeOfServiceZonal typeOfServiceZonal);
}
