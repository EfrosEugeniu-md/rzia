package ru.list.sorfe.rzia.beans;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
public class StCellRollPhasa {
    @Id
    private final TpIcPh id;
    private final TypeOfSecurityElement typeOfSecurityElement;
    private final Integer numberOfSecurityElement;

    public static enum  TypeOfSecurityElement{
        AGR,TP
    }
}

