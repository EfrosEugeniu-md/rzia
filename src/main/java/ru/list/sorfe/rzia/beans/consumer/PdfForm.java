package ru.list.sorfe.rzia.beans.consumer;

import javafx.util.Pair;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.list.sorfe.rzia.beans.Document;
import ru.list.sorfe.rzia.beans.Station;
import ru.list.sorfe.rzia.repository.ConsumerRepository;
import ru.list.sorfe.rzia.repository.ProviderRepository;
import ru.list.sorfe.rzia.repository.StationAndCellularRepository;
import ru.list.sorfe.rzia.repository.StationRepository;
import ru.list.sorfe.rzia.service.CabelService;
import ru.list.sorfe.rzia.service.StationAndCellularService;

@Component
@Getter
public class PdfForm {

    private final StationRepository stationRepository;
    private final StationAndCellularRepository stationAndCellularRepository;
    private final StationAndCellularService stationAndCellularService;
    private final ConsumerRepository consumerRepository;
    private final ProviderRepository providerRepository;
    private final CabelService cabelService;

    private double xSstMax;
    private double xSstMin;

    private double rCable;
    private double xCable;

    private double zSstMax;
    private double zSstMin;
    private double xTrans;


    private double zSstMaxTrans;
    private double zSstMinTrans;

    private double i3Max;
    private double i3Min;
    private double i2Min;

    private double i3MaxTrans;
    private double i3MinTrans;
    private double i2MinTrans;

    private Pair<Double, Boolean> desensitizes;
    private Pair<Double, Boolean> asigurareaSelectivitatii;

    public PdfForm(StationRepository stationRepository,
                   StationAndCellularRepository stationAndCellularRepository,
                   StationAndCellularService stationAndCellularService, ConsumerRepository consumerRepository,
                   ProviderRepository providerRepository,
                   CabelService cabelService) {
        this.stationRepository = stationRepository;
        this.stationAndCellularRepository = stationAndCellularRepository;
        this.stationAndCellularService = stationAndCellularService;
        this.consumerRepository = consumerRepository;
        this.providerRepository = providerRepository;
        this.cabelService = cabelService;
    }

    public void initialization(Document document) {
        Station station = this.stationRepository.findById(document.getTpIc().getTpNumber()).orElse(null);
        assert station != null;
        Consumer consumer = this.consumerRepository.findByTpIc(document.getTpIc());
        assert consumer != null;
        Cabel cabel = consumer.getCabel();
        Provider provider = consumer.getProvider();

        xSstMax = 1.05 * provider.getPower() / (Math.sqrt(3) * provider.getKzMax());
        xSstMin = 1.05 * provider.getPower() / (Math.sqrt(3) * provider.getKzMin());

        rCable = cabelService.getR0(cabel) * cabel.getLengthOfCable() / 1000;
        xCable = cabelService.getX0(cabel) * cabel.getLengthOfCable() / 1000;

        zSstMax = Math.sqrt((xSstMax + xCable) * (xSstMax + xCable) + rCable * rCable);
        zSstMin = Math.sqrt((xSstMin + xCable) * (xSstMin + xCable) + rCable * rCable);

        xTrans = 5.68 * provider.getPower() * provider.getPower() / (0.1 * stationAndCellularService.getPowerTrans(provider));

        zSstMaxTrans = Math.sqrt((xSstMax + xCable + xTrans) * (xSstMax + xCable + xTrans) + rCable * rCable);
        zSstMinTrans = Math.sqrt((xSstMin + xCable + xTrans) * (xSstMin + xCable + xTrans) + rCable * rCable);

        i3Max = provider.getPower() / (Math.sqrt(3) * zSstMax);
        i3Min = provider.getPower() / (Math.sqrt(3) * zSstMin);
        i2Min = 0.87 * i3Min;

        i3MaxTrans = provider.getPower() / (Math.sqrt(3) * zSstMaxTrans);
        i3MinTrans = provider.getPower() / (Math.sqrt(3) * zSstMinTrans);
        i2MinTrans = 0.87 * i3MinTrans;
        double iDesensitizes = 1.6*1.05*consumer.getX().getNumberOfAggregates()
                *consumer.getX().getCurrentOfAggregates()/0.85;
        desensitizes = new Pair<>(iDesensitizes, iDesensitizes < provider.getMtzI());
        asigurareaSelectivitatii= new Pair<>(iDesensitizes, iDesensitizes < provider.getMtzI());
       // Ia.p.,A ≥Кнс(1.3)* Ia.p.(tr)
    }


}
