package ru.list.sorfe.rzia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.list.sorfe.rzia.beans.StCellRollPhasa;
import ru.list.sorfe.rzia.beans.Station;
import ru.list.sorfe.rzia.beans.TpIc;
import ru.list.sorfe.rzia.beans.consumer.Consumer;
import ru.list.sorfe.rzia.beans.consumer.Provider;
import ru.list.sorfe.rzia.beans.station.Rely;
import ru.list.sorfe.rzia.beans.station.StationAndCellular;
import ru.list.sorfe.rzia.repository.StationAndCellularRepository;
import ru.list.sorfe.rzia.service.ConsumerService;
import ru.list.sorfe.rzia.service.StationAndCellularService;
import ru.list.sorfe.rzia.service.StationService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class StationAndCellularServiceImpl implements StationAndCellularService {
    private final StationAndCellularRepository stationAndCellularRepository;
    private final StationService stationService;
    private final ConsumerService consumerService;

    @Autowired
    public StationAndCellularServiceImpl(StationAndCellularRepository stationAndCellularRepository, StationService stationService, ConsumerService consumerService) {
        this.stationAndCellularRepository = stationAndCellularRepository;
        this.stationService = stationService;
        this.consumerService = consumerService;
    }

    @Override
    public StationAndCellular save(StationAndCellular stationAndCellular) {
        return stationAndCellularRepository.save(stationAndCellular);
    }

    @Override
    public StationAndCellular findById(TpIc id) {
        return stationAndCellularRepository.findFirstByTpIc(id).orElse(null);
    }

    @Override
    public List<StationAndCellular> findAll() {
        List<StationAndCellular> result = new ArrayList<>();
        stationAndCellularRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public String getNumberAndProtectedElement(StationAndCellular stationAndCellular) {
        StringBuilder stringBuilder = new StringBuilder("Celula    nr.");
        stringBuilder.append(stationAndCellular.getTpIc().getIc().toString());
        if (stationAndCellular.getTypeOfSecurityElement().equals(StCellRollPhasa.TypeOfSecurityElement.AGR)) {
            stringBuilder.append("  Agregat nr ").append(stationAndCellular.getNumberOfSecurityElement());
        } else if (stationAndCellular.getTypeOfSecurityElement().equals(StCellRollPhasa.TypeOfSecurityElement.TP)) {
            stringBuilder.append("  Linie de cablu spre ST ").append(stationAndCellular.getNumberOfSecurityElement());
        }
        return stringBuilder.toString();
    }

    @Override
    public String getProtectedObject(StationAndCellular stationAndCellular) {
        StringBuilder stringBuilder = new StringBuilder("1. Obiectul protejat:  ");
        Station station = stationService.findById(stationAndCellular.getTpIc().getTpNumber());
        Consumer consumer = station.getConsumers().get(0);

        if (stationAndCellular.getTypeOfSecurityElement().equals(StCellRollPhasa.TypeOfSecurityElement.AGR)) {

            stringBuilder.append("  Transformator, Sn=")
                    .append(this.getPowerTrans(consumer.getProvider()))
                    .append(" kVA, Un=")
                    .append(consumer.getProvider().getPower())
                    .append(".0/0.565 kV. ");
        } else if (stationAndCellular.getTypeOfSecurityElement().equals(StCellRollPhasa.TypeOfSecurityElement.TP)) {

            stringBuilder.append("Linie de cablu L =")
                    .append(consumer.getCabel().getLengthOfCable())
                    .append(" m, S = ")
                    .append(consumer.getCabel().getSectionOfCable())
                    .append(" mmp");
        }
        return stringBuilder.toString();
    }

    @Override
    public String getTypeCell(Station station) {
        String string = "2. Celula de tip: Întrerupătoare de ulei, mecanism de actionare cu electromagnet solenoidal, relee indirecte secundare";
        int[] firstLevel = {1, 2, 3, 4, 5, 6};
        int[] secondLevel = {7, 8, 9};
        int[] thirdLevel = {25, 44, 55};
        if (IntStream.of(firstLevel).anyMatch(x -> x == station.getTpNumber())) {
            string = "2. Celula de tip: Întrerupatoare de ulei, mecanism de actionare cu acumulare de energie in resoarte, relee directă cu descărcare";
        } else if (IntStream.of(secondLevel).anyMatch(x -> x == station.getTpNumber())) {
            string = "2. Celula de tip: Întrerupatoare de ulei, mecanism de actionare cu electromagnet solenoidal, relee directă cu descarcare";
        } else if (IntStream.of(thirdLevel).anyMatch(x -> x == station.getTpNumber())) {
            string = "2. Celula de tip: Intrerupatoare cu vid,mecanism de actionare cu electromagnet solenoidal, relee indirecte secundare";
        }
        return string;
    }

    @Override
    public String getCurentIntrerupere(StationAndCellular stationAndCellular) {
        double curent = Double.MAX_VALUE;
        for (Rely rely : stationAndCellular.getRelies()) {
            double tmp = rely.getToSrabI();
            if (tmp > 0 && tmp < curent) curent = tmp;
        }
        if (curent == Double.MAX_VALUE) return "3. Protecţie de întrerupere   - nu e prevazut";
        int result = (int) curent * stationAndCellular.getTransformationCurrents().get(0).getKTr();
        return "3. Protecţie:      Curent de întrerupere Iср=" + result;
    }

    @Override
    public String getSupracurent(StationAndCellular stationAndCellular) {
        double curent = Double.MAX_VALUE;
        Rely relyResult = null;
        for (Rely rely : stationAndCellular.getRelies()) {
            double tmp = rely.getMtzSrabT();
            if (tmp > 0 && tmp < curent) {
                relyResult = rely;
                curent = tmp;
            }
        }
        if (curent == Double.MAX_VALUE) return "Protecție la supracurent - nu e prevazut";
        int result = (int) relyResult.getMtzSrabI() * stationAndCellular.getTransformationCurrents().get(0).getKTr();
        return "Protecție la supracurent : I=" + result + "A; T= " + relyResult.getMtzSrabT() + "sec.";
    }

    public int getPowerTrans(Provider provider) {
        if (provider.getPower() == 6 && provider.getConsumer().getX().getCurrentOfAggregates() == 67) return 685;
        else if (provider.getPower() == 6 && provider.getConsumer().getX().getCurrentOfAggregates() == 132) return 1385;
        else if (provider.getPower() == 10 && provider.getConsumer().getX().getCurrentOfAggregates() == 40) return 685;
        else if (provider.getPower() == 10 && provider.getConsumer().getX().getCurrentOfAggregates() == 80) return 1385;
        return 0;
    }

    @Override
    public String[][] getTableSecond(StationAndCellular stationAndCellular) {

        String[][] result = new String[4][4];

        result[0][1] = stationAndCellular.getRelies().get(0).getType().toString();
        result[0][2] = "In= " + stationAndCellular.getRelies().get(0).getToSrabI() + " A;";
        result[0][3] = "2";

        if (stationAndCellular.getTypeOfSecurityElement() == StCellRollPhasa.TypeOfSecurityElement.TP  /*cell.AssignmentToProtectionOfCable()*/) {

            if (stationAndCellular.getRelies().get(0).getType() != stationAndCellular.getRelies().get(1).getType()) {
                result[0][1] += " ; " + stationAndCellular.getRelies().get(1).getType();
                result[0][3] = "1 ; 1";
            }

            result[0][0] = "Sectionare de curent";//array[13] = "Токовая отсечка  ";
            result[1][0] = "Sectionare de curent";
            result[1][1] = "RP 23";
            result[1][2] = "Un=220 V;";
            result[1][3] = "1";

            if (stationAndCellular.getRelies().get(0).getMtzSrabT() != 0        /*cell.G1ReleTokovOtsecFazaA != null*/) {
                result[0][0] = "Protectia maximala";//array[13] = "Токовая отсечка  ";
                result[1][0] = "Protectia maximala";
                result[1][1] = "RT 23";
            }
        }


        if (stationAndCellular.getTypeOfSecurityElement() == StCellRollPhasa.TypeOfSecurityElement.AGR  /*cell.AssignmentToProtectionOfCable()*/) {

            result[0][0] = "Sectionare de curent";//array[13] = "Токовая отсечка  ";

            if (stationAndCellular.getRelies().get(0).getType() != stationAndCellular.getRelies().get(2).getType()) {
                result[0][1] += " ; " + stationAndCellular.getRelies().get(2).getType();
                result[0][3] = "1 ; 1";
            }
            result[1][0] = "Protectia de suprasarcina";
            result[1][1] = stationAndCellular.getRelies().get(1).getType().toString();
            result[1][2] = "In= " + stationAndCellular.getRelies().get(1).getMtzSrabI() + " A;";
            result[1][3] = "1";
        }

        result[2][0] = "Circuite de comanda";
        result[2][1] = "Bobina";
        result[2][2] = "Un=220 V.";
        result[2][3] = "1";

        result[3][0] = "Circuite de comanda";
        result[3][1] = "Intrerupătoare АP50";
        result[3][2] = "In=4A,In=16A";
        result[3][3] = "2";

        int[] firstLevel = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        if (IntStream.of(firstLevel).anyMatch(x -> x == stationAndCellular.getTpIc().getTpNumber())) {
            result[2][1] = "Rele RTM";
            result[2][2] = "In=5-10 A.";
            result[2][3] = "2";
            result[3][1] = "Sigurante PRS";
        }
        return result;
    }

    @Override
    public String[][] getTableFourth(StationAndCellular stationAndCellular) {
        String[][] result = {
                {"","","","","",""},
                {"","","","","",""},
                {"","","","","",""},
        };
        int i = 0;
        for (Rely rely : stationAndCellular.getRelies()) {
            if (rely.getMtzSrabT() == 0) {
                result[i][0] = rely.getTpIcPh().getPhase().toString();
                result[i][1] = rely.getType().toString();
                result[i][2] = Double.toString(rely.getToUstI());
                result[i][3] = "" + rely.getToSrabI();
                i++;
            }
        }
        return result;
    }

    @Override
    public String[][] getTableFifth (StationAndCellular stationAndCellular) {
        String[][] result = {
                {"","","","","",""},
                {"","","","","",""},
                {"","","","","",""},
        };
        int i = 0;
        for (Rely rely : stationAndCellular.getRelies()) {
            if (rely.getMtzSrabT() != 0) {
                result[i][0] = rely.getTpIcPh().getPhase().toString();
                result[i][1] = rely.getType().toString();
                result[i][2] = Double.toString(rely.getMtzUstI());
                result[i][3] = "" + rely.getMtzSrabI();
                result[i][4] = "" + rely.getMtzSrabT();
                i++;
            }
        }
        return result;
    }


}
