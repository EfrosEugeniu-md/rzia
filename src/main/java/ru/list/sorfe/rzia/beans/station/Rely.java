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
    private final TpIcPh id;
    private final TypeOfRely type;
    private final double mtzUstI;
    private final double mtzSrabI;
    private final double mtzUstT;
    private final double mtzSrabT;
    private final double toUstI;
    private final double toSrabI;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "tpNumber_id", referencedColumnName = "tpNumber"),
            @JoinColumn(name = "tpType_id", referencedColumnName = "tpType"),
            @JoinColumn(name = "ic_id", referencedColumnName = "ic")
    })
    private StationAndCellular stationAndCellular;

    public static enum TypeOfRely {
        IT81x1, IT81x2, IT82x2, RT40x10, RT40x100, RT40x20, RT40x5, RT40x50,
        RT81x1, RT81x2, RT82x1, RT82x2, RT84x1, RT84x2, RT85x1,
        RT85x2, RT86x1, RT86x2, RT91x1, RT95x1, RT95x2, RSx80M2x8
    }
}
