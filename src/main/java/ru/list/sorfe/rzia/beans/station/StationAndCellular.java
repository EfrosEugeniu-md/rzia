package ru.list.sorfe.rzia.beans.station;

import lombok.*;
import ru.list.sorfe.rzia.beans.StCellRollPhasa;
import ru.list.sorfe.rzia.beans.Station;
import ru.list.sorfe.rzia.beans.TpIc;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE, force = true)
@Entity
public class StationAndCellular {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private final TpIc tpIc;
    private final StCellRollPhasa.TypeOfSecurityElement typeOfSecurityElement;
    private final Integer numberOfSecurityElement;
    @OneToMany(mappedBy = "stationAndCellular", fetch = FetchType.LAZY)
    private List<Rely> relies = new ArrayList<>();
    @OneToMany(mappedBy = "stationAndCellular", fetch = FetchType.LAZY)
    private List<TransformationCurrent> transformationCurrents = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tpNumber_id", referencedColumnName = "tpNumber")
    private Station station;

    @Override
    public String toString() {

        assert this.id != null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ST nr. ").append(this.tpIc.getTpNumber()).append(" iac. ").append(this.tpIc.getIc());
        if (this.typeOfSecurityElement == StCellRollPhasa.TypeOfSecurityElement.TP) {
            stringBuilder.append(" reserve to ST").append(this.numberOfSecurityElement);
        }
//
        if (this.typeOfSecurityElement == StCellRollPhasa.TypeOfSecurityElement.AGR) {
            stringBuilder.append(" aggregate nr.").append(this.numberOfSecurityElement);
        }
//
//        stringBuilder.append("\n");
//
//        for (Rely rely : this.getRelies()) {
//            stringBuilder.append(rely.toString());
//        }
//
//        for (TransformationCurrent transformationCurrent : this.getTransformationCurrents()) {
//            stringBuilder.append(transformationCurrent.toString());
//        }

        return stringBuilder.toString();
    }
}

