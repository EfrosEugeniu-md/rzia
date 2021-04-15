package ru.list.sorfe.rzia.beans;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
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
}
