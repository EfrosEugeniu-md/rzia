package ru.list.sorfe.rzia.service;

import ru.list.sorfe.rzia.beans.consumer.Provider;

import java.util.List;

public interface ProviderService {
    Provider save(Provider provider);

    List<Provider> findAll();
}
