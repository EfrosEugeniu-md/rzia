package ru.list.sorfe.rzia.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.list.sorfe.rzia.beans.consumer.Provider;
import ru.list.sorfe.rzia.repository.ProviderRepository;
import ru.list.sorfe.rzia.service.ProviderService;
import ru.list.sorfe.rzia.util.ConsumerUtil;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProviderServiceImpl implements ProviderService {
    private final ProviderRepository repository;

    @Autowired
    public ProviderServiceImpl(ProviderRepository repository) {
        this.repository = repository;
//        for (Provider provider : ConsumerUtil.providers) {
//            repository.save(provider);
//        }

    }

    @Override
    public Provider save(Provider provider) {
        return repository.save(provider);
    }

    @Override
    public List<Provider> findAll() {
        List<Provider> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        return result;
    }
}
