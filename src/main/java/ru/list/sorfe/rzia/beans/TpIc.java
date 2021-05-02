package ru.list.sorfe.rzia.beans;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Embeddable
public class TpIc implements Serializable {
    private final Integer tpNumber;
    private final TypeOfStation tpType;
    private final Integer ic;

    public static enum TypeOfStation {
        CRP, RP, TP
    }

    public String toString() {

        return tpType.toString() + tpNumber.toString() + " cellular nr. " + ic.toString();
    }
}
