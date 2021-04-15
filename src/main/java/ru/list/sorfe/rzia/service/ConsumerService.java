package ru.list.sorfe.rzia.service;

import ru.list.sorfe.rzia.beans.consumer.Consumer;

import java.util.List;

public interface ConsumerService {
    Consumer save(Consumer consumer);

    List<Consumer> findAll();
}
