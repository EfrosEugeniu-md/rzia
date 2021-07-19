package ru.list.sorfe.rzia.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.list.sorfe.rzia.beans.consumer.Cabel;
import ru.list.sorfe.rzia.repository.CabelRepository;
import ru.list.sorfe.rzia.service.CabelService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CabelServiceImpl implements CabelService {

    private final CabelRepository repository;

    @Autowired
    public CabelServiceImpl(CabelRepository repository) {
        this.repository = repository;
    }

    @Override
    public Cabel save(Cabel cabel) {
        return repository.save(cabel);
    }

    @Override
    public List<Cabel> findAll() {
        List<Cabel> result = new ArrayList<Cabel>();
        repository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public double getR0(Cabel cabel) {
        int sectionOfCable = cabel.getSectionOfCable();
        double result;
        switch (sectionOfCable) {
            case 140:
                result = 0.218;
                break;
            case 150:
                result = 0.208;
                break;
            case 185:
                result = 0.169;
                break;
            case 240:
                result = 0.13;
                break;
            case 300:
                result = 0.104;
                break;
            default:
                result = 0.261;
        }
        return result;
    }

    @Override
    public double getX0(Cabel cabel) {
        int sectionOfCable = cabel.getSectionOfCable();
        double result = 0;
        if (cabel.getConsumer().getProvider().getPower() == 6) {
            switch (sectionOfCable) {
                case 140:
                    result = 0.275;
                    break;
                case 150:
                    result = 0.074;
                    break;
                case 185:
                    result = 0.073;
                    break;
                case 240:
                    result = 0.071;
                    break;
                case 300:
                    result = 0.070;
                    break;
                default:
                    result = 0.076;
            }
        } else if (cabel.getConsumer().getProvider().getPower() == 10) {
            switch (sectionOfCable) {
                case 140:
                    result = 0.08;
                    break;
                case 150:
                    result = 0.079;
                    break;
                case 185:
                    result = 0.077;
                    break;
                case 240:
                    result = 0.075;
                    break;
                case 300:
                    result = 0.074;
                    break;
                default:
                    result = 0.081;
            }
        }

        return result;
    }
}
