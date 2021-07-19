package ru.list.sorfe.rzia.web;

import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.list.sorfe.rzia.beans.Document;
import ru.list.sorfe.rzia.beans.SetOfDocuments;
import ru.list.sorfe.rzia.beans.Station;
import ru.list.sorfe.rzia.beans.consumer.Consumer;
import ru.list.sorfe.rzia.beans.station.StationAndCellular;
import ru.list.sorfe.rzia.repository.DocumentRepository;
import ru.list.sorfe.rzia.repository.SetOfDocumentsRepository;
import ru.list.sorfe.rzia.repository.StationRepository;
import ru.list.sorfe.rzia.util.GeneratePdfReport;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

@Controller
public class PdfController {
    @Autowired
    GeneratePdfReport generatePdfReport;
    @Autowired
    StationRepository stationRepository;
    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    SetOfDocumentsRepository setOfDocumentsRepository;


    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePDF(HttpServletResponse response,
                                                           @RequestParam Optional<String> id,
                                                           @RequestParam Optional<String> type) throws Exception {

        SetOfDocuments setOfDocuments = new SetOfDocuments(LocalDate.now());

        Station station = stationRepository
                .findById(Integer.parseInt(id.orElse("0")))
                .orElse(null);

        Document.TypeOfDocument typeOfDocument = Document.TypeOfDocument.valueOf(type.orElse("CELL"));

        assert station != null;

        if (typeOfDocument.equals(Document.TypeOfDocument.CELL)) {
            for (StationAndCellular stationAndCellular : station.getStationAndCellulars()) {
                Document document = new Document(stationAndCellular.getTpIc(), LocalDate.now(),
                        setOfDocuments, Document.TypeOfDocument.CELL);
                setOfDocuments.getDocuments().add(document);
            }
        } else {
            for (Consumer consumer : station.getConsumers()) {
                Document document = new Document(consumer.getTpIc(), LocalDate.now(),
                        setOfDocuments, Document.TypeOfDocument.CONS);
                setOfDocuments.getDocuments().add(document);
            }
        }

        return getInputStreamResourceResponseEntity(response, setOfDocuments);
    }

    @GetMapping(value = "/pdfSets", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePDFSets(HttpServletResponse response,
                                                               @RequestParam Optional<String> id,
                                                               @RequestParam Optional<String> type) throws Exception {

        SetOfDocuments setOfDocuments = setOfDocumentsRepository
                .findById(Long.parseLong(id.orElse("0")))
                .orElse(null);

        return getInputStreamResourceResponseEntity(response, setOfDocuments);
    }

    @GetMapping(value = "/pdfDocument", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePDF_Document(HttpServletResponse response,
                                                                    @RequestParam Optional<String> id,
                                                                    @RequestParam Optional<String> type) throws Exception {

        Document document = documentRepository
                .findById(Long.parseLong(id.orElse("0")))
                .orElse(null);

        SetOfDocuments setOfDocuments = new SetOfDocuments(LocalDate.now());

        setOfDocuments.setDescription(document.getTpIc().toString() +"  "+ document.getDateOfWork().toString());

        setOfDocuments.getDocuments().add(document);

        return getInputStreamResourceResponseEntity(response, setOfDocuments);
    }

    private ResponseEntity<InputStreamResource> getInputStreamResourceResponseEntity(HttpServletResponse response, SetOfDocuments setOfDocuments) throws JRException, IOException {
        JasperPrint jasperPrint = generatePdfReport.getJasperPrint(setOfDocuments);

        byte[] bis = JasperExportManager.exportReportToPdf(jasperPrint);
        InputStream targetStream = new ByteArrayInputStream(bis);

        response.setContentType("application/x-pdf");
        HttpHeaders headers = new HttpHeaders();
        String contentDisposition="inline; filename=Process"+setOfDocuments.getDescription()+".pdf";
        headers.add("Content-Disposition", contentDisposition);

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(targetStream));
    }

    private JasperPrint getJasperPrint(Optional<String> id, Optional<String> type) throws JRException, IOException {


        if (type.orElse("none").equals("CELL")) {

            Station station = stationRepository
                    .findById(Integer.parseInt(id.orElse("0")))
                    .orElse(null);

            assert station != null;
            Queue<StationAndCellular> queue = new LinkedList<>(station.getStationAndCellulars());

            InputStream inputStream = this.getClass().getResourceAsStream("/jasper/myJasper.jrxml");

            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            JasperPrint jasperPrint = JasperFillManager
                    .fillReport(
                            jasperReport,
                            getStationAndCellularMap(Objects.requireNonNull(queue.poll())),
                            new JREmptyDataSource());

            for (StationAndCellular stationAndCellular : queue) {

                JasperPrint jasperPrint_next = JasperFillManager
                        .fillReport(
                                jasperReport,
                                getStationAndCellularMap(stationAndCellular),
                                new JREmptyDataSource()
                        );

                List pages = jasperPrint_next.getPages();

                for (Object page : pages) {
                    JRPrintPage object = (JRPrintPage) page;
                    jasperPrint.addPage(object);
                }
            }

            inputStream.close();

            return jasperPrint;
        } else if (type.orElse("none").equals("CONS")) {
            Station station = stationRepository
                    .findById(Integer.parseInt(id.orElse("0")))
                    .orElse(null);

            assert station != null;
            Queue<Consumer> queue = new LinkedList<>(station.getConsumers());

            InputStream inputStream = this.getClass().getResourceAsStream("/jasper/my3.jrxml");

            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            JasperPrint jasperPrint = JasperFillManager
                    .fillReport(
                            jasperReport,
                            getConsumerMap(Objects.requireNonNull(queue.poll())),
                            new JREmptyDataSource());

            for (Consumer consumer : queue) {

                JasperPrint jasperPrint_next = JasperFillManager
                        .fillReport(
                                jasperReport,
                                getConsumerMap(consumer),
                                new JREmptyDataSource()
                        );

                List pages = jasperPrint_next.getPages();

                for (Object page : pages) {
                    JRPrintPage object = (JRPrintPage) page;
                    jasperPrint.addPage(object);
                }
            }

            inputStream.close();

            return jasperPrint;
        }
        return null;
    }

    private Map<String, Object> getStationAndCellularMap(StationAndCellular stationAndCellular) {
        Map<String, Object> params = new HashMap<>();
        params.put("Parameter1", stationAndCellular.getTpIc().toString());
        params.put("Parameter2", "Melnic A.");
        params.put("Parameter3", "Celula    nr. 4 Rezervați intrarea ТП 13");
        params.put("Parameter4", "16 апреля 2019");
        params.put("Parameter5", "verificări de protecție a releului și circuite secundare de conexiune de 10 kV");
        params.put("Parameter6", "1. Obiect protejat: Linie de cablu L = 1750 m, S = 185 mp Mm.");
        params.put("Parameter7", "2. Tipul celulei Întrerupătoare de ulei, acționare electromagnetică, relee indirecte secundare");
        params.put("Parameter8", "3. Protecţie:      Curent de întrerupere Iср=660");
        params.put("Parameter9", "Protecție la supracurent - nu e prevazut");
        params.put("Parameter10", "ТПЛМ10");
        params.put("Parameter11", "150");
        params.put("Parameter12", "30");
        params.put("Parameter13", "celula Nr.4");
        params.put("Parameter14", "Curent de întrerupere");
        params.put("Parameter15", "РТ40/20");
        params.put("Parameter16", "Iср= 22 A;");
        params.put("Parameter17", "2");
        params.put("Parameter18", "Curent de întrerupere");
        params.put("Parameter19", "РТ40/20");
        params.put("Parameter20", "Iср= 22 A;");
        params.put("Parameter21", "Iср= 22 A;");
        params.put("Parameter22", "Curent de întrerupere");
        params.put("Parameter23", "РТ40/20");
        params.put("Parameter24", "Iср= 22 A;");
        params.put("Parameter25", "Iср= 22 A;");
        params.put("Parameter26", "Curent de întrerupere");
        params.put("Parameter27", "РТ40/20");
        params.put("Parameter28", "Iср= 22 A;");
        params.put("Parameter29", "Iср= 22 A;");
        return params;
    }

    private Map<String, Object> getConsumerMap(Consumer consumer) {
        Map<String, Object> params = new HashMap<>();
        params.put("Parameter1", consumer.getProvider().getTpIc().toString());
        return params;
    }

}
