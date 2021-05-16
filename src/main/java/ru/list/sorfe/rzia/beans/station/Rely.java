package ru.list.sorfe.rzia.beans.station;

import lombok.*;
import ru.list.sorfe.rzia.beans.TpIcPh;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
public class Rely {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private final TpIcPh tpIcPh;
    private final TypeOfRely type;
    private final double mtzUstI;
    private final double mtzSrabI;
    private final double mtzUstT;
    private final double mtzSrabT;
    private final double toUstI;
    private final double toSrabI;

    @ManyToOne(fetch = FetchType.LAZY)
    private StationAndCellular stationAndCellular;

    public static enum TypeOfRely {
        IT81x1, IT81x2, IT82x2, RT40x10, RT40x100, RT40x20, RT40x5, RT40x50,
        RT81x1, RT81x2, RT82x1, RT82x2, RT84x1, RT84x2, RT85x1,
        RT85x2, RT86x1, RT86x2, RT91x1, RT95x1, RT95x2, RSx80M2x8
    }

    @Override
    public String toString() {
        assert this.type != null;
        assert this.id != null;
        StringBuilder stringBuilder = (new StringBuilder("Rely in " + this.id.toString()
                + " , type :" + this.type.toString().replace('x', '-')));
        if (this.mtzUstI != 0) {
            stringBuilder.append("MTZ : I= ").append(this.mtzUstI)
                    .append("/").append(this.mtzSrabI).append(" A; T=")
                    .append(this.mtzUstT).append("/").append(this.mtzSrabT).append(" sec.");
        }
        if (this.toUstI != 0) {
            stringBuilder.append("TO : I= ").append(this.toUstI)
                    .append("/").append(this.toSrabI).append(" A.").append("\n");
        }
        return stringBuilder.toString();
    }
}
