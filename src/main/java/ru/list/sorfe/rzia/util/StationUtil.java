package ru.list.sorfe.rzia.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import ru.list.sorfe.rzia.beans.StCellRollPhasa;
import ru.list.sorfe.rzia.beans.Station;
import ru.list.sorfe.rzia.beans.TpIcPh;
import ru.list.sorfe.rzia.beans.station.Rely;
import ru.list.sorfe.rzia.beans.station.StationAndCellular;
import ru.list.sorfe.rzia.beans.station.TransTipGod;
import ru.list.sorfe.rzia.beans.station.TransformationCurrent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ru.list.sorfe.rzia.util.CommonUtil.*;

public class StationUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(StationUtil.class.getName());
    public static List<TransformationCurrent> transList = new ArrayList<>();
    public static List<Rely> releList = new ArrayList<>();
    public static Set<StationAndCellular> stCellHashSet = new HashSet<>();
    public static Set<Station> stHashSet = new HashSet<>();

    static {
        InputStream resource = null;
        try {
            resource = new ClassPathResource(
                    "baza1.txt").getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] elements = line.split("\\t");
                if (elements.length > 2) {

                    try {
                        StCellRollPhasa stCellRollPhasa = ConvertToStCellRollPhasa(elements[0]);

                        StationAndCellular stationAndCellular = getStCell(stCellRollPhasa);
                        stCellHashSet.add(stationAndCellular);

                        Station station = new Station(stCellRollPhasa.getId().getTpIc().getTpNumber());
                        stHashSet.add(station);

                        TransformationCurrent transformationCurrent = getTransformatorCurent(elements, stCellRollPhasa);
                        if (transformationCurrent != null) transList.add(transformationCurrent);

                        Rely rely = getRele(elements, stCellRollPhasa);
                        if (rely != null) {
                            // rely.setStationAndCellular(stationAndCellular);
                            releList.add(rely);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static StationAndCellular getStCell(StCellRollPhasa stCellRollPhasa) {
        return new StationAndCellular(
                stCellRollPhasa.getId().getTpIc(),
                stCellRollPhasa.getTypeOfSecurityElement(),
                stCellRollPhasa.getNumberOfSecurityElement()
        );
    }

    private static StCellRollPhasa ConvertToStCellRollPhasa(String inputStr) {
        String noSpaceStr = inputStr.replace("\u0000", "");

        int index = lowestIndexOf(noSpaceStr, "AGR");
        String typeOfSecurityElementStr = (index > 0) ? "AGR" : "";
        String numberOfSecurityElementStr = (index > 0) ? noSpaceStr.substring(index + 3, index + 5) : "";
        String tpIcStr = (index > 0) ? noSpaceStr.substring(0, index) : "";

        index = lowestIndexOf(noSpaceStr, "TP");
        typeOfSecurityElementStr = (index > 3) ? "TP" : typeOfSecurityElementStr;
        numberOfSecurityElementStr = (index > 3) ? noSpaceStr.substring(index + 2, index + 4) : numberOfSecurityElementStr;
        tpIcStr = (index > 3) ? noSpaceStr.substring(0, index) : tpIcStr;

        index = lowestIndexOf(noSpaceStr, "F");
        String phasa = (index > 0) ? noSpaceStr.substring(index) : "";
        return new StCellRollPhasa(
                new TpIcPh(ConvertToTpIc(tpIcStr), TpIcPh.TypeOfPhase.valueOf(phasa)),
                StCellRollPhasa.TypeOfSecurityElement.valueOf(typeOfSecurityElementStr),
                ParseInt(numberOfSecurityElementStr));
    }

    private static TransformationCurrent getTransformatorCurent(String[] elements, StCellRollPhasa stCellRollPhasa) {
        String noSpaceStr = elements[1].replace("\u0000", "");
        if (noSpaceStr.trim().isEmpty()) return null;
        return new TransformationCurrent(
                stCellRollPhasa.getId(),
                ConvertToTransTipGod(elements[1]),
                ParseInt(elements[2]),
                ParseInt(elements[3])
        );
    }

    private static TransTipGod ConvertToTransTipGod(String inputStr) {
        String noSpaceStr = inputStr.replace("\u0000", "");
        int index = lowestIndexOf(noSpaceStr, "G");
        String typeOfTransfCurentStr = (index > 0) ? noSpaceStr.substring(0, index) : "";
        String ageManufacturingStr = (index > 0) ? noSpaceStr.substring(index + 1) : "22";
        return new TransTipGod(
                TransTipGod.TypeOfTransfCurent.valueOf(typeOfTransfCurentStr),
                ParseInt(ageManufacturingStr));
    }

    private static Rely getRele(String[] elements, StCellRollPhasa stCellRollPhasa) {
        Rely rele = null;

        if (!elements[4].trim().isEmpty() || !elements[5].trim().isEmpty()) {
            int index = 0;
            if (elements[1].trim().isEmpty() && !elements[2].trim().isEmpty()) index = 1;

            String typeOfReleStr = elements[4 + index]
                    .replace("/", "x")
                    .replace("-", "x")
                    .replace("\u0000", "");

            rele = new Rely(
                    stCellRollPhasa.getId(),
                    Rely.TypeOfRely.valueOf(typeOfReleStr),
                    ParseDouble(elements[5 + index]),
                    ParseDouble(elements[6 + index]),
                    ParseDouble(elements[7 + index]),
                    ParseDouble(elements[8 + index]),
                    ParseDouble(elements[9 + index]),
                    ParseDouble(elements[10 + index])
            );
        }
        return rele;
    }
}
