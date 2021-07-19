package ru.list.sorfe.rzia.util;

import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;
import ru.list.sorfe.rzia.beans.Document;
import ru.list.sorfe.rzia.beans.SetOfDocuments;
import ru.list.sorfe.rzia.beans.Station;
import ru.list.sorfe.rzia.beans.consumer.Cabel;
import ru.list.sorfe.rzia.beans.consumer.Consumer;
import ru.list.sorfe.rzia.beans.consumer.PdfForm;
import ru.list.sorfe.rzia.beans.consumer.Provider;
import ru.list.sorfe.rzia.beans.station.Personal;
import ru.list.sorfe.rzia.beans.station.StationAndCellular;
import ru.list.sorfe.rzia.repository.*;
import ru.list.sorfe.rzia.service.CabelService;
import ru.list.sorfe.rzia.service.StationAndCellularService;

import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class GeneratePdfReport {
    private final StationRepository stationRepository;
    private final StationAndCellularRepository stationAndCellularRepository;
    private final StationAndCellularService stationAndCellularService;
    private final ConsumerRepository consumerRepository;
    private final ProviderRepository providerRepository;
    private final CabelService cabelService;
    private final ReleRepository releRepository;
    private final TransRepository transRepository;
    private final PersonalRepository personalRepository;
    private final PdfForm pdfForm;

    public GeneratePdfReport(StationRepository stationRepository,
                             StationAndCellularRepository stationAndCellularRepository,
                             StationAndCellularService stationAndCellularService, ConsumerRepository consumerRepository,
                             ProviderRepository providerRepository,
                             CabelService cabelService, ReleRepository releRepository,
                             TransRepository transRepository,
                             PersonalRepository personalRepository, PdfForm pdfForm) {
        this.stationRepository = stationRepository;
        this.stationAndCellularRepository = stationAndCellularRepository;
        this.stationAndCellularService = stationAndCellularService;
        this.consumerRepository = consumerRepository;
        this.providerRepository = providerRepository;
        this.cabelService = cabelService;
        this.releRepository = releRepository;
        this.transRepository = transRepository;
        this.personalRepository = personalRepository;
        this.pdfForm = pdfForm;
    }


    public JasperPrint getJasperPrint(SetOfDocuments setOfDocuments) throws JRException, IOException {

        Queue<Document> queueDocument = new LinkedList<>(setOfDocuments.getDocuments());

        JasperPrint jasperPrint = getJasperPrintFromDocument(Objects.requireNonNull(queueDocument.poll()));


        for (Document document : queueDocument) {

            JasperPrint jasperPrint_next = getJasperPrintFromDocument(document);

            assert jasperPrint_next != null;
            List pages = jasperPrint_next.getPages();

            for (Object page : pages) {
                JRPrintPage object = (JRPrintPage) page;
                assert jasperPrint != null;
                jasperPrint.addPage(object);
            }
        }
        return jasperPrint;

    }

    private JasperPrint getJasperPrintFromDocument(Document document) throws JRException, IOException {


        if (document.getTypeOfDocument().equals(Document.TypeOfDocument.CELL)) {
            InputStream inputStream = document.getClass().getResourceAsStream("/jasper/myJasper.jrxml");

            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            inputStream.close();

            JasperPrint jasperPrint = JasperFillManager
                    .fillReport(
                            jasperReport,
                            getStationAndCellularMapPageFirst(document),
                            new JREmptyDataSource());

            inputStream = document.getClass().getResourceAsStream("/jasper/my2.jrxml");

            jasperReport = JasperCompileManager.compileReport(inputStream);

            JasperPrint jasperPrint_next = JasperFillManager
                    .fillReport(
                            jasperReport,
                            getStationAndCellularMapPageSecond(document),
                            new JREmptyDataSource()
                    );

            List pages = jasperPrint_next.getPages();

            for (Object page : pages) {
                JRPrintPage object = (JRPrintPage) page;
                jasperPrint.addPage(object);
            }
            return jasperPrint;


        } else if (document.getTypeOfDocument().equals(Document.TypeOfDocument.CONS)) {
            InputStream inputStream = document.getClass().getResourceAsStream("/jasper/my3.jrxml");

            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            inputStream.close();

            return JasperFillManager
                    .fillReport(
                            jasperReport,
                            getConsumerMap(document),
                            new JREmptyDataSource());

        }
        return null;
    }


    private Map<String, Object> getStationAndCellularMapPageFirst(Document document) {
        Map<String, Object> params = new HashMap<>();

        Station station = stationRepository.findById(document.getTpIc().getTpNumber()).orElse(null);
        assert station != null;
        StationAndCellular stationAndCellular = stationAndCellularRepository.findFirstByTpIc(document.getTpIc()).orElse(null);
        assert stationAndCellular != null;

        params.put("Parameter1", "ST" + stationAndCellular.getTpIc().getTpNumber().toString());
        params.put("Parameter2", Objects.requireNonNull(personalRepository.findFirstByTypeOfPosition(Personal.TypeOfPosition.SEF_GEN).orElse(null)).getName());
        params.put("Parameter3", stationAndCellularService.getNumberAndProtectedElement(stationAndCellular));
        params.put("Parameter4", document.getDateOfWork().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        params.put("Parameter5", "de verificare a protectiei prin relee si circuite secundare de conexiune  " + station.getConsumers().get(0).getProvider().getPower() + " kV");
        params.put("Parameter6", stationAndCellularService.getProtectedObject(stationAndCellular));
        params.put("Parameter7", stationAndCellularService.getTypeCell(station));
        params.put("Parameter8", stationAndCellularService.getCurentIntrerupere(stationAndCellular));
        params.put("Parameter9", stationAndCellularService.getSupracurent(stationAndCellular));
        params.put("Parameter10", stationAndCellular.getTransformationCurrents().get(0).getTransTipGod().getTypeOfTransfCurent().toString());
        params.put("Parameter11", ((stationAndCellular.getTransformationCurrents().get(0).getKTr() * 5) + "/5"));
        params.put("Parameter12", stationAndCellular.getTransformationCurrents().get(0).getKTr() + "");
        params.put("Parameter13", "celula Nr." + stationAndCellular.getTpIc().getIc());

        String[][] strings = stationAndCellularService.getTableSecond(stationAndCellular);

        params.put("Parameter14", strings[0][0]);
        params.put("Parameter15", strings[0][1]);
        params.put("Parameter16", strings[0][2]);
        params.put("Parameter17", strings[0][3]);

        params.put("Parameter18", strings[1][0]);
        params.put("Parameter19", strings[1][1]);
        params.put("Parameter20", strings[1][2]);
        params.put("Parameter21", strings[1][3]);

        params.put("Parameter22", strings[2][0]);
        params.put("Parameter23", strings[2][1]);
        params.put("Parameter24", strings[2][2]);
        params.put("Parameter25", strings[2][3]);

        params.put("Parameter26", strings[3][0]);
        params.put("Parameter27", strings[3][1]);
        params.put("Parameter28", strings[3][2]);
        params.put("Parameter29", strings[3][3]);
        return params;
    }

    private Map<String, Object> getStationAndCellularMapPageSecond(Document document) {
        Map<String, Object> params = new HashMap<>();

        Station station = stationRepository.findById(document.getTpIc().getTpNumber()).orElse(null);
        assert station != null;
        Consumer consumer = station.getConsumers().get(0);
        StationAndCellular stationAndCellular = stationAndCellularRepository.findFirstByTpIc(document.getTpIc()).orElse(null);
        assert stationAndCellular != null;

        String[][] strings = stationAndCellularService.getTableFourth (stationAndCellular);

        params.put("Parameter1", strings[0][0]);
        params.put("Parameter2", strings[0][1]);
        params.put("Parameter3", strings[0][2]);
        params.put("Parameter4", strings[0][3]);

        params.put("Parameter5", strings[1][0]);
        params.put("Parameter6", strings[1][1]);
        params.put("Parameter7", strings[1][2]);
        params.put("Parameter8", strings[1][3]);

        params.put("Parameter9", strings[2][0]);
        params.put("Parameter10", strings[2][1]);
        params.put("Parameter11", strings[2][2]);
        params.put("Parameter12", strings[2][3]);

        String[][] strings1 = stationAndCellularService.getTableFifth(stationAndCellular);

        params.put("Parameter13", strings1[0][0]);
        params.put("Parameter14", strings1[0][1]);
        params.put("Parameter15", strings1[0][2]);
        params.put("Parameter16", strings1[0][3]);
        params.put("Parameter17", strings1[0][4]);

        params.put("Parameter18", strings1[1][0]);
        params.put("Parameter19", strings1[1][1]);
        params.put("Parameter20", strings1[1][2]);
        params.put("Parameter21", strings1[1][3]);
        params.put("Parameter22", strings1[1][4]);

        params.put("Parameter23", strings1[2][0]);
        params.put("Parameter24", strings1[2][1]);
        params.put("Parameter25", strings1[2][2]);
        params.put("Parameter26", strings1[2][3]);
        params.put("Parameter27", strings1[2][4]);


        params.put("Parameter28",""+ stationAndCellular.getTransformationCurrents().get(0).getKTr());
        params.put("Parameter29", "1  А");
        params.put("Parameter30", "nr. " + stationAndCellular.getTpIc().getIc()
                + "   ST " + stationAndCellular.getTpIc().getTpNumber() + "    .");

        params.put("Parameter31", Objects.requireNonNull(personalRepository
                .findFirstByTypeOfPositionAndTypeOfServiceZonal(Personal.TypeOfPosition.MASTER
                        , consumer.getServiceZonal()).orElse(null)).getName());
        params.put("Parameter32", Objects.requireNonNull(personalRepository
                .findFirstByTypeOfPosition(Personal.TypeOfPosition.MEMBER).orElse(null)).getName());
        params.put("Parameter33", Objects.requireNonNull(personalRepository
                .findFirstByTypeOfPosition(Personal.TypeOfPosition.SEF_BRD).orElse(null)).getName());
        params.put("Parameter34", Objects.requireNonNull(personalRepository
                .findFirstByTypeOfPosition(Personal.TypeOfPosition.CONTROL).orElse(null)).getName());
        return params;
    }

    private Map<String, Object> getConsumerMap(Document document) {
        Map<String, Object> params = new HashMap<>();

        Station station = stationRepository.findById(document.getTpIc().getTpNumber()).orElse(null);
        assert station != null;
        Consumer consumer = consumerRepository.findByTpIc(document.getTpIc());
        assert consumer != null;
        Cabel cabel = consumer.getCabel();
        Provider provider = consumer.getProvider();

        pdfForm.initialization(document);


        params.put("Parameter1", document.getTpIc().toString());
        params.put("Parameter2", Objects.requireNonNull(personalRepository.findFirstByTypeOfPosition(Personal.TypeOfPosition.SEF_GEN).orElse(null)).getName());
        params.put("Parameter3", "Celula    nr. 4 Rezervați intrarea ТП 13");
        params.put("Parameter4", provider.getTpIc().getTpType() +
                "  " + provider.getTpIc().getTpNumber());
        params.put("Parameter5", provider.getTpIc().getIc().toString());
        params.put("Parameter6", provider.getPower() + "");
        params.put("Parameter7", (int) (provider.getKzMax() * 1000) + "");
        params.put("Parameter8", (int) (provider.getKzMin() * 1000) + "");
        params.put("Parameter9", "ASB");
        params.put("Parameter10", cabel.getSectionOfCable() + "");
        params.put("Parameter11", cabel.getLengthOfCable() + "");
        double r0 = cabelService.getR0(cabel), x0 = cabelService.getX0(cabel);
        params.put("Parameter12", r0 + "");
        params.put("Parameter13", x0 + "");
        params.put("Parameter14", "TMPYM");
        params.put("Parameter15", consumer.getX().getNumberOfAggregates() + "");
        params.put("Parameter16", stationAndCellularService.getPowerTrans(provider) + "");
        params.put("Parameter17", consumer.getX().getCurrentOfAggregates() + "");
        params.put("Parameter18", "5.68");
        params.put("Parameter19", provider.getMtzI() == 0 ? "  -" : (provider.getMtzI() + ""));
        params.put("Parameter20", provider.getMtzT() == 0 ? "  -" : (provider.getMtzT() + ""));
        params.put("Parameter21", provider.getTo() == 0 ? "  -" : (provider.getTo() + ""));
        params.put("Parameter22", "Curent de întrerupere");
        params.put("Parameter23", "РТ40/20");
        params.put("Parameter24", "Iср= 22 A;");
        params.put("Parameter25", String.format("%.3f", pdfForm.getXSstMax()));
        params.put("Parameter26", String.format("%.3f", pdfForm.getXSstMin()));
        params.put("Parameter27", String.format("%.3f", pdfForm.getRCable()));
        params.put("Parameter28", String.format("%.3f", pdfForm.getXCable()));
        params.put("Parameter29", String.format("%.3f", pdfForm.getZSstMax()));
        params.put("Parameter30", String.format("%.3f", pdfForm.getZSstMin()));
        params.put("Parameter31", String.format("%.3f", pdfForm.getXTrans()));
        params.put("Parameter32", String.format("%.3f", pdfForm.getZSstMaxTrans()));
        params.put("Parameter33", String.format("%.3f", pdfForm.getZSstMinTrans()));
        params.put("Parameter34", String.format("%.0f", (pdfForm.getI3Max()) * 1000));
        params.put("Parameter35", String.format("%.0f", (pdfForm.getI3Min()) * 1000));
        params.put("Parameter36", String.format("%.0f", (pdfForm.getI2Min()) * 1000));
        params.put("Parameter37", String.format("%.0f", (pdfForm.getI3MaxTrans()) * 1000));
        params.put("Parameter38", String.format("%.0f", (pdfForm.getI3MinTrans()) * 1000));
        params.put("Parameter39", String.format("%.0f", (pdfForm.getI2MinTrans()) * 1000));
        params.put("Parameter40", "Iср= 22 A;");
        params.put("Parameter41", "Iср= 22 A;");
        params.put("Parameter42", "Iср= 22 A;");
        params.put("Parameter43", "Iср= 22 A;");
        params.put("Parameter44", "Iср= 22 A;");
        params.put("Parameter45", "Iср= 22 A;");
        params.put("Parameter46", "Iср= 22 A;");
        params.put("Parameter47", "Iср= 22 A;");
        params.put("Parameter48", "Iср= 22 A;");
        params.put("Parameter49", "Iср= 22 A;");
        params.put("Parameter50", Objects.requireNonNull(personalRepository.findFirstByTypeOfPosition(Personal.TypeOfPosition.SEF_BRD).orElse(null)).getName());
        params.put("Parameter51", Objects.requireNonNull(personalRepository.findFirstByTypeOfPosition(Personal.TypeOfPosition.SEF_ADJ).orElse(null)).getName());
        return params;
    }

}
