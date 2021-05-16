package ru.list.sorfe.rzia.beans.station;

import lombok.*;
import ru.list.sorfe.rzia.beans.TpIcPh;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
@Table(name = "current")
public class TransformationCurrent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private final TpIcPh tpIcPh;
    private final TransTipGod transTipGod;
    private final int idTr;
    private final int kTr;

    @ManyToOne(fetch = FetchType.LAZY)
    private StationAndCellular stationAndCellular;

    @Override
    public String toString() {

        assert this.transTipGod != null;
        assert this.id != null;
        StringBuilder stringBuilder = (new StringBuilder("Transformation off Current in " + this.id.toString()
                + " , type :" + this.transTipGod.getTypeOfTransfCurent().toString()));

        stringBuilder.append(this.kTr * 5).append("/5 A, Manufacturing in  ");

        if (this.transTipGod.getAgeManufacturing() > 50) {
            stringBuilder.append("19").append(this.transTipGod.getAgeManufacturing());
        } else {
            stringBuilder.append("20").append(this.transTipGod.getAgeManufacturing());
        }
        stringBuilder.append(" , Ktr =  ").append(this.kTr).append("\n");

        return stringBuilder.toString();
    }
}
