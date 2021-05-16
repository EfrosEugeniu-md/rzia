package ru.list.sorfe.rzia.beans;

import lombok.*;
import org.springframework.core.convert.converter.Converter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@Embeddable
public class TpIc implements Serializable {
    private final Integer tpNumber;
    private final TypeOfStation tpType;
    private final Integer ic;

    public static enum TypeOfStation {
        CRP, RP, TP
    }

    public String toString() {
        return tpType.toString() + "_" + tpNumber.toString() + "_" + ic.toString();
    }

    public static TpIc toTpIC(String str) {
        String[] strArray = str.split("_");

        return new TpIc(
                Integer.parseInt(strArray[1]),
                TypeOfStation.valueOf(strArray[0]),
                Integer.parseInt(strArray[2])
        );
    }
}
