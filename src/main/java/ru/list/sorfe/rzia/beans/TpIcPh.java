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
public
class TpIcPh implements Serializable {
    private final TpIc tpIc;
    private final TypeOfPhase phase;

    public static enum TypeOfPhase {
        FA, FO, FC
    }
    @Override
    public String toString(){

        return tpIc.toString()+" phase:"+phase.toString();
    }
}
