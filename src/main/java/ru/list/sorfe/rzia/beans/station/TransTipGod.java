package ru.list.sorfe.rzia.beans.station;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Embeddable
public class TransTipGod {
    private final TypeOfTransfCurent typeOfTransfCurent;
    private final int ageManufacturing;

    public static enum TypeOfTransfCurent {
        TPFM10, TPLM10, TPL10, TLM10, TOL10, TVLM10, TLK10, ABB
    }
}
