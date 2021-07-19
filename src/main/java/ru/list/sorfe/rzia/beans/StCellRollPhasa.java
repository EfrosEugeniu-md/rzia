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

    public boolean equals(Object other) {
        if ((this == other)) return true;
        if ((other == null)) return false;
        if (!(other instanceof StCellRollPhasa)) return false;
        StCellRollPhasa castOther = (StCellRollPhasa) other;

        return ( this.getId().getTpIc() != null
                && this.getId().getTpIc().getTpType().equals(castOther.getId().getTpIc().getTpType())
                && this.getId().getTpIc().getTpNumber().equals(castOther.getId().getTpIc().getTpNumber())
                && this.getId().getTpIc().getIc().equals(castOther.getId().getTpIc().getIc())
        );
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + (this.getId().getTpIc().getTpType() == null ? 0 : this.getId().getTpIc().getTpType().hashCode());
        result = 37 * result + (this.getId().getTpIc().getTpNumber() == null ? 0 : this.getId().getTpIc().getTpNumber().hashCode());
        result = 37 * result + (this.getId().getTpIc().getIc() == null ? 0 : this.getId().getTpIc().getIc().hashCode());

        return result;
    }
}

