package ru.list.sorfe.rzia.beans.station;

import lombok.*;
import ru.list.sorfe.rzia.beans.TpIcPh;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
public class TransformationCurrent {
    @Id
    private final TpIcPh id;
    private final TransTipGod transTipGod;
    private final int idTr;
    private final int kTr;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "tpNumber_id", referencedColumnName = "tpNumber"),
            @JoinColumn(name = "tpType_id", referencedColumnName = "tpType"),
            @JoinColumn(name = "ic_id", referencedColumnName = "ic")
    })
    private StationAndCellular stationAndCellular;
}
